#!/bin/bash
# Usage: bash claude_eval.sh <Project> <StartBug> <EndBug>
export JAVA_HOME="/opt/homebrew/opt/openjdk@11"
export PATH="$JAVA_HOME/bin:$PATH"

P="$1"; S="$2"; E="$3"
[ -z "$P" ] || [ -z "$S" ] || [ -z "$E" ] && { echo "Usage: $0 <Project> <StartBug> <EndBug>"; exit 1; }

D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
BASE="/Users/suphawat/Documents/SQA/defect4j_test"
CLAUDE="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Claude-sonnet_5_5"
RES="$CLAUDE/Result/$P"
LOGS="$RES/final_logs"
OUT="$RES/claude_final_results.csv"
STAGE_ROOT="/tmp/claude_suite_$P"
mkdir -p "$LOGS" "$STAGE_ROOT"

echo "bug,test_files,methods,compile_buggy,test_buggy,compile_fixed,test_fixed,fault_detected,lines_covered,lines_total,line_pct,cond_covered,cond_total,cond_pct,note" > "$OUT"

run_one() { # $1=dir $2=tag(b|f) ; uses TAR, N
  cd "$1" || { R_C="NO_DIR"; R_T="NOT_RUN"; return; }
  rm -f summary.csv failing_tests
  "$D4J" coverage -s "$TAR" > "$LOGS/$P-$N$2.log" 2>&1
  if [ ! -f summary.csv ]; then R_C="FAIL"; R_T="NOT_RUN"; return; fi
  R_C="PASS"
  if [ -s failing_tests ]; then R_T="FAIL"; else R_T="PASS"; fi
}

for N in $(seq "$S" "$E"); do
  TDIR="$CLAUDE/TestCode/$P/$P-$N"
  B="$BASE/checkouts/$P/$P-${N}b"
F="$BASE/checkouts/$P/$P-${N}f"
  FILES=$(find "$TDIR" -name "*.java" 2>/dev/null)
  NF=$(echo "$FILES" | grep -c .)
  NOTE=""

  if [ "$NF" = "0" ]; then
    echo "${N},0,0,,,,,NO,,,,,,,NO_TEST" >> "$OUT"
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
    echo "${N},${NF},,,,,,NO,,,,,,,CHECKOUT_FAILED" >> "$OUT"
    echo "$P-$N: CHECKOUT_FAILED"; continue
  fi

  # stage archive, check name collision with existing Defects4J tests
  STAGE="$STAGE_ROOT/$P-$N"
  rm -rf "$STAGE"; mkdir -p "$STAGE"
  METHODS=0; COLLISION=0
  while IFS= read -r FILE; do
    [ -z "$FILE" ] && continue
    NAME=$(basename "$FILE")
    if find "$F/src/test" -name "$NAME" 2>/dev/null | grep -q .; then COLLISION=1; fi
    PKG=$(grep -m1 '^package ' "$FILE" | sed 's/package //; s/;.*//' | tr -d ' \r')
    mkdir -p "$STAGE/$(echo "$PKG" | tr '.' '/')"
    cp "$FILE" "$STAGE/$(echo "$PKG" | tr '.' '/')/"
    M=$(grep -c '@Test' "$FILE")
    [ "$M" = "0" ] && M=$(grep -c 'public void test' "$FILE")
    METHODS=$((METHODS + M))
  done <<< "$FILES"

  if [ "$COLLISION" = "1" ]; then
    echo "${N},${NF},${METHODS},,,,,NO,,,,,,,COLLISION" >> "$OUT"
    echo "$P-$N: COLLISION (test name already exists in checkout)"; continue
  fi

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

  echo "${N},${NF},${METHODS},${CB},${TB},${CF},${TF},${FD},${LC},${LT},${LP},${CC},${CT},${CP},${NOTE}" >> "$OUT"
  echo "$P-$N: buggy ${CB}/${TB} | fixed ${CF}/${TF} | detected ${FD} | line ${LP}% | cond ${CP}%"
done

echo ""
column -s, -t "$OUT"
echo ""
awk -F, 'NR>1 {n++; if($8=="YES") y++; if($11!=""){k++; l+=$11; c+=$14}}
END {if(n>0 && k>0) printf "Detected %d/%d = %.2f%% | Avg line %.1f%% | Avg cond %.1f%% (coverage from %d bugs)\n", y, n, y/n*100, l/k, c/k, k}' "$OUT"
