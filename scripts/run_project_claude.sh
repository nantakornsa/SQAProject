#!/bin/bash
# Usage: bash run_project_claude.sh <Project> <stage> [Start End]
# stages: ids | prompt | api | eval | summary

P="$1"; ST="$2"; S="$3"; E="$4"
if [ -z "$P" ] || [ -z "$ST" ]; then
  echo "Usage: bash $0 <Project> <ids|prompt|api|eval|summary> [Start End]"; exit 1
fi

D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
BASE="/Users/suphawat/Documents/SQA/defect4j_test"
AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Claude-sonnet_5_5"
EVAL="$BASE/scripts/claude_eval2.sh"
RES="$AI/Result/$P"
MERGED="$RES/claude_auto_merged.csv"

j11() { export JAVA_HOME="/opt/homebrew/opt/openjdk@11"; export PATH="$JAVA_HOME/bin:$PATH"; }
j17() { export JAVA_HOME="/opt/homebrew/opt/openjdk@17"; export PATH="$JAVA_HOME/bin:$PATH"; }
need_range() { [ -z "$S" ] || [ -z "$E" ] && { echo "stage $ST needs Start End"; exit 1; }; }

case "$ST" in

ids)
  j11
  LIST=$("$D4J" query -p "$P" -q bug.id 2>/dev/null)
  echo "$P has $(echo "$LIST" | grep -c .) bugs:"
  echo "$LIST" | tr '\n' ' '; echo
  if [ -n "$S" ] && [ -n "$E" ]; then
    MISS=""
    for N in $(seq "$S" "$E"); do echo "$LIST" | grep -qx "$N" || MISS="$MISS $N"; done
    [ -n "$MISS" ] && echo "NOT in Defects4J:$MISS" || echo "range $S-$E all exist"
  fi
  ;;

prompt)
  need_range
  j17
  cd "$AI" || exit 1
  mvn -q compile exec:java -Dexec.mainClass="com.example.claudesonnet.PromptGenerator" -Dexec.args="$P $S $E"
  PF=$(find "$AI/Prompt/$P" -type f 2>/dev/null)
  if [ -z "$PF" ]; then echo "No prompt files under $AI/Prompt/$P - send me: ls $AI"; exit 1; fi
  echo "prompt files: $(echo "$PF" | grep -c .)"
  echo "prompts without MODIFIED SOURCE:"
  echo "$PF" | while IFS= read -r f; do grep -L 'MODIFIED SOURCE' "$f"; done
  ;;

api)
  need_range
  KL=${#KKU_API_KEY}
  if [ "$KL" != "67" ]; then echo "KKU_API_KEY length is $KL, expected 67. export it in this window first."; exit 1; fi
  echo "About to call the API for $P bugs $S-$E. THIS COSTS TOKENS."
  read -r -p "Continue? (y/N) " ANS
  [ "$ANS" = "y" ] || { echo "cancelled"; exit 0; }
  j17
  cd "$AI" || exit 1
  mvn -q compile exec:java -Dexec.mainClass="com.example.claudesonnet.ClaudeClient" -Dexec.args="$P $S $E"
  echo ""
  echo "== check responses"
  BAD=""
  for N in $(seq "$S" "$E"); do
    R="$RES/$P-$N/raw_response.txt"
    if [ ! -s "$R" ] || ! grep -q '"stop_reason":"end_turn"' "$R"; then BAD="$BAD $N"; fi
    ls "$AI/TestCode/$P/$P-$N"/*.java > /dev/null 2>&1 || BAD="$BAD $N(no_test_file)"
  done
  [ -n "$BAD" ] && echo "PROBLEM bugs:$BAD" || echo "all $S-$E OK (end_turn and test file present)"
  ;;

eval)
  need_range
  j11
  echo "== rename test classes that collide with checkout"
  for N in $(seq "$S" "$E"); do
    TDIR="$AI/TestCode/$P/$P-$N"
    F="$BASE/checkouts/$P/$P-${N}f"
    [ -d "$TDIR" ] || continue
    if [ ! -d "$F" ]; then "$D4J" checkout -p "$P" -v "${N}f" -w "$F" > /dev/null 2>&1; fi
    [ -d "$F" ] || { echo "$P-$N: checkout f failed"; continue; }
    for FILE in "$TDIR"/*.java; do
      [ -f "$FILE" ] || continue
      NAME=$(basename "$FILE")
      CLASS="${NAME%.java}"
      if find "$F/src/test" "$F/test" "$F/tests" -name "$NAME" 2>/dev/null | grep -q .; then
        mkdir -p "$RES/$P-$N/original_name"
        [ -f "$RES/$P-$N/original_name/$NAME" ] || cp "$FILE" "$RES/$P-$N/original_name/$NAME"
        CNT=$(grep -c "\b$CLASS\b" "$FILE")
        perl -pi -e 's/\b'"$CLASS"'\b/Claude'"$CLASS"'/g' "$FILE"
        mv "$FILE" "$TDIR/Claude$NAME"
        MSG="$P-$N: $CLASS -> Claude$CLASS (lines containing the name: $CNT)"
        [ "$CNT" -gt 4 ] && MSG="$MSG  CHECK: name appears often"
        echo "$MSG"
      fi
    done
  done
  echo "== measure"
  bash "$EVAL" "$P" "$S" "$E" | grep -E "^$P-|^Detected|COLLISION"
  ;;

summary)
  if [ ! -f "$MERGED" ]; then echo "No $MERGED - run the eval stage first"; exit 1; fi
  OUT="$RES/Claude_${P}_Summary.md"
  {
    echo "Claude $P Summary"
    echo ""
    echo "Model: claude-sonnet-5.5 per config"
    echo ""
    echo "| Bug | Detected | Line % | Cond % | Note |"
    echo "|---|---|---|---|---|"
    awk -F, 'NR>1 {l=$11; c=$14; if (l=="") l="-"; if (c=="") c="-"; n="";
      if ($15 ~ /NO_TEST|CHECKOUT_FAILED|COLLISION/) n="";
      else if ($8=="NO" && $4!="PASS") n="compile error, check log";
      else if ($8=="NO" && $5=="PASS" && $7=="PASS") n="pass on both";
      else if ($8=="NO") n="fail on both";
      m=n; if ($15!="") { if (m!="") m=m"; "; m=m $15 }
      print "| "$1" | "$8" | "l" | "c" | "m" |"}' "$MERGED"
    echo ""
    echo "Summary"
    awk -F, 'NR>1 {n++; if($8=="YES") y++; if($11!=""){k++; l+=$11; c+=$14}}
      END {printf "Detected %d of %d = %.2f percent (all bugs in the project range)\n", y, n, y/n*100;
           if (k>0) printf "Avg line coverage %.1f percent and avg cond coverage %.1f percent, from %d bugs with coverage\n", l/k, c/k, k}' "$MERGED"
    echo "Fault detected means the test fails on buggy and passes on fixed, with both versions compiling."
    echo "TODO: check compile errors in logs. Errors in the test itself count as not detected. Errors from helper classes or the script are not measurable and should be reported separately."
    echo ""
    echo "Not detected"
    awk -F, 'NR>1 && $8=="NO" {
      if ($15 ~ /NO_TEST|CHECKOUT_FAILED|COLLISION/) ot=ot (ot==""?"":", ") $1;
      else if ($4!="PASS") ce=ce (ce==""?"":", ") $1;
      else if ($5=="PASS" && $7=="PASS") pb=pb (pb==""?"":", ") $1;
      else fb=fb (fb==""?"":", ") $1 }
      END {print "Compile error, check log: " ce; print "Pass on both buggy and fixed: " pb; print "Fail on both: " fb; print "No test, checkout failed or name collision: " ot}' "$MERGED"
    echo ""
    echo "Notes"
    echo "Tests not edited or regenerated. Renamed tests (prefix Claude) keep originals in original_name."
    echo "Coverage is measured on the fixed version, only on classes touched by the bug fix, so it is low by design."
    echo "Rows with 0.0 percent coverage may mean no coverage data. Check lines_total in the csv."
    echo ""
    echo "Method"
    echo "Measured with Defects4J on Java 11 using claude_eval2.sh. Claude and Claude ran different projects so they cannot be compared directly."
  } > "$OUT"
  echo "written: $OUT"
  head -60 "$OUT"
  ;;

*)
  echo "unknown stage: $ST"; exit 1 ;;
esac
