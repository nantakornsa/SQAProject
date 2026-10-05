#!/bin/bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@11"
export PATH="$JAVA_HOME/bin:$PATH"

P="$1"; S="$2"; E="$3"
[ -z "$P" ] || [ -z "$S" ] || [ -z "$E" ] && { echo "Usage: $0 <Project> <StartBug> <EndBug>"; exit 1; }

D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
BASE="/Users/suphawat/Documents/SQA/defect4j_test"
AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Gemini-3_8-flash"
RES="$AI/Result/$P"
LOGS="$RES/final_logs"
PERBUG="$RES/per_bug"
MERGED="$RES/gemini_auto_merged.csv"
STAGE_ROOT="/tmp/gemini_suite_$P"
LOCK="$RES/.eval_lock"
HEADER="bug,test_files,methods,compile_buggy,test_buggy,compile_fixed,test_fixed,fault_detected,lines_covered,lines_total,line_pct,cond_covered,cond_total,cond_pct,note"
mkdir -p "$LOGS" "$STAGE_ROOT" "$PERBUG"

if ! mkdir "$LOCK" 2>/dev/null; then
  echo "Another eval for $P seems to be running (lock: $LOCK). If not, delete that folder and retry."
  exit 1
fi
trap 'rmdir "$LOCK" 2>/dev/null' EXIT

run_one() {
  cd "$1" || { R_C="NO_DIR"; R_T="NOT_RUN"; return; }
  rm -f summary.csv failing_tests
  "$D4J" coverage -s "$TAR" > "$LOGS/$P-$N$2.log" 2>&1
  if [ ! -f summary.csv ]; then R_C="FAIL"; R_T="NOT_RUN"; return; fi
  R_C="PASS"
  R_T="PASS"
  if grep -q "^--- " failing_tests 2>/dev/null; then
    if grep "^--- " failing_tests | grep -Evq -- "^--- (${HREGEX:-NOHELPER})(::.*)?\$"; then R_T="FAIL"; fi
  fi
}

add_helpers() {
  local cur="$1" depth=0 sup hf hpkg
  while [ "$depth" -lt 4 ]; do
    sup=$(tr '\n\r' '  ' < "$cur" | grep -m1 -oE 'class +[A-Za-z0-9_]+ +extends +[A-Za-z0-9_]+' | head -1 | awk '{print $4}')
    [ -z "$sup" ] && break
    [ "$sup" = "TestCase" ] && break
    hf=$(find "$F/src/test" "$F/test" "$F/tests" -name "$sup.java" 2>/dev/null | head -1)
    [ -z "$hf" ] && break
    hpkg=$(grep -m1 '^package ' "$hf" | sed 's/package //; s/;.*//' | tr -d ' \r')
    mkdir -p "$STAGE/$(echo "$hpkg" | tr '.' '/')"
    cp "$hf" "$STAGE/$(echo "$hpkg" | tr '.' '/')/"
    if grep -q "core.testsupport" "$hf"; then
      mkdir -p "$STAGE/com/fasterxml/jackson/core/testsupport"
      cp "$F"/src/test/java/com/fasterxml/jackson/core/testsupport/*.java "$STAGE/com/fasterxml/jackson/core/testsupport/"
      HREGEX="${HREGEX:+$HREGEX|}com.fasterxml.jackson.core.testsupport.[A-Za-z0-9_]+"
    fi
    HREGEX="${HREGEX:+$HREGEX|}$hpkg.$sup"
    HLIST="$HLIST $hpkg.$sup"
    cur="$hf"; depth=$((depth + 1))
  done
}

for N in $(seq "$S" "$E"); do
  TDIR="$AI/TestCode/$P/$P-$N"
  B="$BASE/checkouts/$P/$P-${N}b"
  F="$BASE/checkouts/$P/$P-${N}f"
  OUT_N="$PERBUG/$N.csv"
  FILES=$(find "$TDIR" -name "*.java" 2>/dev/null)
  NF=$(echo "$FILES" | grep -c .)
  NOTE=""

  if [ "$NF" = "0" ]; then
    echo "${N},0,0,,,,,NO,,,,,,,NO_TEST" > "$OUT_N"
    echo "$P-$N: NO_TEST"; continue
  fi
  [ "$NF" -gt 1 ] && NOTE="MULTI_FILE"

  if [ ! -d "$B" ]; then
    "$D4J" checkout -p "$P" -v "${N}b" -w "$B" > "$LOGS/$P-$N-checkout-b.log" 2>&1
  fi
  if [ ! -d "$F" ]; then
    "$D4J" checkout -p "$P" -v "${N}f" -w "$F" > "$LOGS/$P-$N-checkout-f.log" 2>&1
  fi
  if [ ! -d "$B" ] || [ ! -d "$F" ]; then
    echo "${N},${NF},,,,,,NO,,,,,,,CHECKOUT_FAILED" > "$OUT_N"
    echo "$P-$N: CHECKOUT_FAILED"; continue
  fi

  STAGE="$STAGE_ROOT/$P-$N"
  rm -rf "$STAGE"; mkdir -p "$STAGE"
  METHODS=0; COLLISION=0; HREGEX=""; HLIST=""
  while IFS= read -r FILE; do
    [ -z "$FILE" ] && continue
    NAME=$(basename "$FILE")
    if find "$F/src/test" "$F/test" "$F/tests" -name "$NAME" 2>/dev/null | grep -q .; then COLLISION=1; fi
    PKG=$(grep -m1 '^package ' "$FILE" | sed 's/package //; s/;.*//' | tr -d ' \r')
    mkdir -p "$STAGE/$(echo "$PKG" | tr '.' '/')"
    cp "$FILE" "$STAGE/$(echo "$PKG" | tr '.' '/')/"
    M=$(grep -c '@Test' "$FILE")
    [ "$M" = "0" ] && M=$(grep -c 'public void test' "$FILE")
    METHODS=$((METHODS + M))
    add_helpers "$FILE"
    for IMP in $(grep -oE '^import +[A-Za-z0-9_.]+;' "$FILE" | sed -E 's/^import +//; s/;//'); do
      IREL="$(echo "$IMP" | tr '.' '/').java"
      IHF="$F/src/test/java/$IREL"
      [ -f "$IHF" ] || continue
      mkdir -p "$STAGE/$(dirname "$IREL")"
      cp "$IHF" "$STAGE/$IREL"
      add_helpers "$IHF"
      HREGEX="${HREGEX:+$HREGEX|}$IMP"
      HLIST="$HLIST $IMP"
    done
    if grep -q "org.mockitoutil" "$FILE"; then
      mkdir -p "$STAGE/org/mockitoutil"
      for hf2 in "$F"/test/org/mockitoutil/*.java; do
        case "$hf2" in *Test.java|*ExtraMatchers.java) continue;; esac
        cp "$hf2" "$STAGE/org/mockitoutil/"
        HREGEX="${HREGEX:+$HREGEX|}org.mockitoutil.$(basename "$hf2" .java)"
      done
    fi
    if grep -q "org.mockitoutil" "$FILE"; then
      for rel in org/mockito/StateMaster org/mockito/internal/configuration/ConfigurationAccess org/mockito/configuration/MockitoConfiguration org/mockitousage/configuration/SmartMock org/mockitousage/configuration/ClassToBeMocked org/mockitousage/IMethods; do
        [ -f "$F/test/$rel.java" ] || continue
        mkdir -p "$STAGE/$(dirname "$rel")"
        cp "$F/test/$rel.java" "$STAGE/$rel.java"
        HREGEX="${HREGEX:+$HREGEX|}$(echo "$rel" | tr '/' '.')"
      done
    fi
  done <<< "$FILES"

  if [ "$COLLISION" = "1" ]; then
    echo "${N},${NF},${METHODS},,,,,NO,,,,,,,COLLISION" > "$OUT_N"
    echo "$P-$N: COLLISION (test name already exists in checkout)"; continue
  fi

  [ -n "$HLIST" ] && NOTE="${NOTE:+$NOTE }HELPER:${HLIST# }"
  NOTE=$(echo "$NOTE" | tr ',' ';')

  TAR="$STAGE_ROOT/$P-$N.tar.bz2"
  tar -cjf "$TAR" -C "$STAGE" .

  run_one "$B" b; CB=$R_C; TB=$R_T
  run_one "$F" f; CF=$R_C; TF=$R_T

  FD="NO"
  [ "$CB" = PASS ] && [ "$TB" = FAIL ] && [ "$CF" = PASS ] && [ "$TF" = PASS ] && FD="YES"

  LC=""; LT=""; LP=""; CC=""; CT=""; CP=""
  if [ -f "$F/summary.csv" ]; then
    IFS=, read -r LT LC CT CC <<< "$(tail -n 1 "$F/summary.csv")"
    LP=$(awk -v a="$LC" -v b="$LT" 'BEGIN{ if (b>0) printf "%.1f", a/b*100; else print "0.0" }')
    CP=$(awk -v a="$CC" -v b="$CT" 'BEGIN{ if (b>0) printf "%.1f", a/b*100; else print "0.0" }')
  fi

  echo "${N},${NF},${METHODS},${CB},${TB},${CF},${TF},${FD},${LC},${LT},${LP},${CC},${CT},${CP},${NOTE}" > "$OUT_N"
  echo "$P-$N: buggy ${CB}/${TB} | fixed ${CF}/${TF} | detected ${FD} | line ${LP}% | cond ${CP}% | ${NOTE}"
done

echo "$HEADER" > "$MERGED"
cat "$PERBUG"/*.csv | sort -t, -k1,1n >> "$MERGED"

echo ""
echo "Merged file: $MERGED"
column -s, -t "$MERGED"
echo ""
awk -F, 'NR>1 {n++; if($8=="YES") y++; if($11!=""){k++; l+=$11; c+=$14}}
END {if(n>0 && k>0) printf "Detected %d/%d = %.2f%% | Avg line %.1f%% | Avg cond %.1f%% (coverage from %d bugs)\n", y, n, y/n*100, l/k, c/k, k}' "$MERGED"
