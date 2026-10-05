#!/bin/bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@11"
export PATH="$JAVA_HOME/bin:$PATH"

D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
BASE="/Users/suphawat/Documents/SQA/defect4j_test"
RES="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Claude-sonnet_4_6/Result/Codec"
COV="$RES/claude_coverage_results.csv"
OUT="$RES/claude_final_results.csv"
LOGS="$RES/final_logs"
mkdir -p "$LOGS" /tmp/claude_suite

echo "bug,compile_buggy,test_buggy,compile_fixed,test_fixed,fault_detected,lines_covered,lines_total,line_pct,cond_covered,cond_total,cond_pct" > "$OUT"

run_one() { # $1=dir $2=bug $3=tag(b|f) ; sets R_COMPILE R_TEST
  local DIR="$1" BUG="$2" TAG="$3"
  cd "$DIR" || { R_COMPILE="NO_DIR"; R_TEST="NOT_RUN"; return; }
  rm -f summary.csv failing_tests
  local LOG="$LOGS/Codec-${BUG}${TAG}.log"
  if [ "$BUG" = "1" ]; then
    local F="$BASE/Codec-1f/src/test/org/apache/commons/codec/language/ClaudeCaverphoneTest.java"
    cp "$F" "$DIR/src/test/org/apache/commons/codec/language/"
    "$D4J" coverage -t "org.apache.commons.codec.language.ClaudeCaverphoneTest::testLocaleIndependence" > "$LOG" 2>&1
  else
    "$D4J" coverage -s "/tmp/claude_suite/Codec-${BUG}.tar.bz2" > "$LOG" 2>&1
  fi
  if grep -q "Couldn't obtain\|Cannot compile\|died" "$LOG" || [ ! -f summary.csv ]; then
    R_COMPILE="FAIL"; R_TEST="NOT_RUN"; return
  fi
  R_COMPILE="PASS"
  if [ -s failing_tests ]; then R_TEST="FAIL"; else R_TEST="PASS"; fi
}

for BUG in $(seq 1 18); do
  run_one "$BASE/Codec-${BUG}b" "$BUG" b; CB=$R_COMPILE; TB=$R_TEST
  run_one "$BASE/Codec-${BUG}f" "$BUG" f; CF=$R_COMPILE; TF=$R_TEST
  FD="NO"
  [ "$CB" = PASS ] && [ "$TB" = FAIL ] && [ "$CF" = PASS ] && [ "$TF" = PASS ] && FD="YES"
  CV=$(awk -F, -v b="$BUG" 'NR>1 && $1==b {print $7","$6","$10","$9","$8","$11}' "$COV")
  echo "${BUG},${CB},${TB},${CF},${TF},${FD},${CV}" >> "$OUT"
  echo "Codec-${BUG}: buggy ${CB}/${TB} | fixed ${CF}/${TF} | detected ${FD}"
done

echo ""
column -s, -t "$OUT"
echo ""
awk -F, 'NR>1 {n++; if($6=="YES") y++; l+=$9; c+=$12} END {printf "Detected %d/%d = %.2f%% | Avg line %.1f%% | Avg cond %.1f%%\n", y, n, y/n*100, l/n, c/n}' "$OUT"
