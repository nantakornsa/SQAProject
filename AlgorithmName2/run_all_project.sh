#!/bin/bash
# Usage:
#   ./run_all_project.sh                       # everything
#   ONLY_PROJECT=mockito ONLY_BUG=1 ./run_all_project.sh   # single smoke test
#   JOBS=6 TIMEOUT=10m ./run_all_project.sh
#
# Each job runs in its own throw-away copy of pom.xml + src/ (tiny), so parallel
# jobs never share target/ and no state leaks between bugs.
set -u

SRC="$(cd "$(dirname "$0")" && pwd)"
OUT="${OUT:-$SRC/Result_Round2}"
WORK_ROOT="${WORK_ROOT:-/tmp/bugrun}"          # use a Linux filesystem (NOT /mnt/c, NOT OneDrive)
M2_REPO="${M2_REPO:-$HOME/.m2/repository}"
NPROC="$(nproc)"
JOBS="${JOBS:-$(( NPROC > 1 ? NPROC / 2 : 1 ))}"
TIMEOUT="${TIMEOUT:-5m}"
ONLY_PROJECT="${ONLY_PROJECT:-}"
ONLY_BUG="${ONLY_BUG:-}"

# name : number of bugs : target class : Maven artifactId under groupId "defects4j"
PROJECTS=(
    "mockito:38:org.mockito.internal.invocation.InvocationMatcher:mockito-buggy"
    "cli:39:org.apache.commons.cli.CommandLine:cli-buggy"
    "codec:18:org.apache.commons.codec.binary.Base64:codec-buggy"
    "collections:28:org.apache.commons.collections4.ListUtils:collections-buggy"
    "csv:16:org.apache.commons.csv.CSVFormat:csv-buggy"
    "gson:18:com.google.gson.Gson:gson-buggy"
    "jsoup:93:org.jsoup.nodes.Document:jsoup-buggy"
    "lang:65:org.apache.commons.lang3.StringUtils:lang-buggy"
    "math:106:org.apache.commons.math3.util.Precision:math-buggy"
    "closure:133:com.google.javascript.jscomp.Compiler:closure-buggy"
    "lang_f:65:org.apache.commons.lang3.StringUtils:lang-fixed"
    "time:27:org.joda.time.DateTime:time-buggy"
    "compress:47:org.apache.commons.compress.archivers.ArchiveStreamFactory:compress-buggy"
    "jacksoncore:26:com.fasterxml.jackson.core.JsonFactory:jacksoncore-buggy"
    "jacksondatabind:112:com.fasterxml.jackson.databind.ObjectMapper:jacksondatabind-buggy"
    "jacksonxml:6:com.fasterxml.jackson.dataformat.xml.XmlMapper:jacksonxml-buggy"
    "jxpath:22:org.apache.commons.jxpath.JXPathContext:jxpath-buggy"
)

mkdir -p "$OUT" "$WORK_ROOT"

cov_of() {
    local csv=$1 fqcn=$2
    local pkg="${fqcn%.*}" cls="${fqcn##*.}"
    awk -F, -v p="$pkg" -v c="$cls" \
        'NR>1 && $2==p && ($3==c || index($3, c "$")==1) {m+=$4; v+=$5} END {printf "%d,%d", v, m+v}' "$csv"
}

finish() {
    local tmp=$1 dest=$2 proj=$3 i=$4 status=$5 cov=$6 tot=$7 cov_text=$8 secs=$9
    echo "$status" > "$tmp/status"
    echo "$proj,$i,$status,$cov,$tot,$cov_text,$secs" > "$tmp/summary.line"
    rm -rf "$dest"
    mv "$tmp" "$dest"
    touch "$dest/.done"
    printf '[%s] %-15s bug_%-4s %-12s %s (%ss)\n' "$(date +%T)" "$proj" "$i" "$status" "$cov_text" "$secs"
}

run_one() {
    local proj=$1 i=$2 target=$3 art=$4
    local ver="${i}.0"
    local dest="$OUT/$proj/bug_$i"
    local tmp="$OUT/$proj/.bug_${i}.tmp"

    # ข้ามทันทีถ้าเคยรัน PASS ไปแล้วในรอบก่อนหน้า
    if [ -f "$dest/.done" ] && [ "$(cat "$dest/status" 2>/dev/null)" = "PASS" ]; then
        return 0
    fi

    mkdir -p "$OUT/$proj"
    rm -rf "$tmp"; mkdir -p "$tmp"

    if [ ! -d "$M2_REPO/defects4j/$art/$ver" ]; then
        echo "artifact missing: defects4j:$art:$ver in $M2_REPO" > "$tmp/run.log"
        finish "$tmp" "$dest" "$proj" "$i" "NO_ARTIFACT" 0 0 "N/A (0/0)" 0
        return 0
    fi

    local work
    work="$(mktemp -d "$WORK_ROOT/${proj}_${i}.XXXXXX")"
    cp "$SRC/pom.xml" "$work/"
    cp -r "$SRC/src" "$work/"

    local start=$SECONDS
    ( cd "$work" && timeout "$TIMEOUT" mvn -B --no-transfer-progress -o \
        -Dmaven.test.failure.ignore=true \
        -Dd4j.artifact="$art" -Dd4j.version="$ver" \
        -DtargetClass="$target" \
        -Dtest='Algorithm2Test#testParameterizedFuzzer' \
        test-compile surefire:test jacoco:report ) > "$tmp/run.log" 2>&1
    local rc=$?
    local secs=$(( SECONDS - start ))

    [ -d "$work/target/site/jacoco" ]      && cp -r "$work/target/site/jacoco"      "$tmp/coverage_report"
    [ -d "$work/target/surefire-reports" ] && cp -r "$work/target/surefire-reports" "$tmp/test_logs"
    ls "$work"/crash-* >/dev/null 2>&1 && { mkdir -p "$tmp/crashes"; cp "$work"/crash-* "$tmp/crashes/"; }

    local status
    if [ "$rc" -eq 124 ]; then
        status="TIMEOUT"
    elif [ "$rc" -ne 0 ]; then
        status="BUILD_ERROR"
    elif grep -q "no fuzzable methods" "$tmp/run.log" 2>/dev/null \
         || grep -q "no fuzzable methods" "$tmp"/test_logs/* 2>/dev/null; then
        status="NO_METHODS"
    elif ! ls "$tmp"/test_logs/TEST-*.xml >/dev/null 2>&1; then
        status="NO_TESTS"
    elif grep -q "Cannot load" "$tmp"/test_logs/TEST-*.xml 2>/dev/null; then
        status="LOAD_FAIL"
    elif grep -qE '<(failure|error)' "$tmp"/test_logs/TEST-*.xml; then
        status="CRASH"
    else
        status="PASS"
    fi

    local cov=0 tot=0
    if [ -f "$tmp/coverage_report/jacoco.csv" ]; then
        IFS=, read -r cov tot <<< "$(cov_of "$tmp/coverage_report/jacoco.csv" "$target")"
    fi

    local cov_text="N/A ($cov/$tot)"
    if [ "$tot" -gt 0 ]; then
        cov_text="$(awk -v c="$cov" -v t="$tot" 'BEGIN { printf "%.2f%% (%d/%d instructions)", 100*c/t, c, t }')"
    fi

    finish "$tmp" "$dest" "$proj" "$i" "$status" "$cov" "$tot" "$cov_text" "$secs"
    rm -rf "$work"
}

export -f run_one cov_of finish
export SRC OUT WORK_ROOT M2_REPO TIMEOUT

START=$SECONDS
echo "JOBS=$JOBS  TIMEOUT=$TIMEOUT  WORK_ROOT=$WORK_ROOT  M2_REPO=$M2_REPO"

{
    for ENTRY in "${PROJECTS[@]}"; do
        IFS=':' read -r PROJ BUGS TARGET ART <<< "$ENTRY"
        [ -n "$ONLY_PROJECT" ] && [ "$PROJ" != "$ONLY_PROJECT" ] && continue
        for (( i=1; i<=BUGS; i++ )); do
            [ -n "$ONLY_BUG" ] && [ "$i" != "$ONLY_BUG" ] && continue
            echo "$PROJ $i $TARGET $ART"
        done
    done
} | xargs -P "$JOBS" -L1 bash -c 'run_one "$@"' _

# summary
{
    echo "project,bug,status,target_instr_covered,target_instr_total,target_instr_coverage_text,seconds"
    cat "$OUT"/*/bug_*/summary.line 2>/dev/null | awk -F, 'BEGIN { OFS="," } NF==6 { if ($5 > 0) display=sprintf("%.2f%% (%d/%d instructions)", 100*$4/$5, $4, $5); else display=sprintf("N/A (%d/%d)", $4, $5); print $1,$2,$3,$4,$5,display,$6; next } { print }' | sort -t, -k1,1 -k2,2n
} > "$OUT/summary.csv"

echo "=================================================="
echo " เสร็จสิ้น ใช้เวลา $(( (SECONDS - START) / 60 )) นาที"
echo " สรุปสถานะ:"
cut -d, -f3 "$OUT/summary.csv" | tail -n +2 | sort | uniq -c
echo " รายละเอียด: $OUT/summary.csv"
echo "=================================================="
