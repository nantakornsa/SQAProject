#!/bin/bash
P="$1"; S="$2"; E="$3"
if [ -z "$P" ] || [ -z "$S" ] || [ -z "$E" ]; then echo "Usage: bash run_all.sh <Project> <Start> <End>"; exit 1; fi
D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
export JAVA_HOME="/opt/homebrew/opt/openjdk@11"; export PATH="$JAVA_HOME/bin:$PATH"
LIST=$("$D4J" query -p "$P" -q bug.id 2>/dev/null)
if [ -z "$LIST" ]; then echo "Could not get bug ids for $P"; exit 1; fi
RUNS=""; START=""; PREV=""
for N in $(seq "$S" "$E"); do
  if echo "$LIST" | grep -qx "$N"; then
    if [ -z "$START" ]; then START=$N; PREV=$N
    elif [ "$N" -eq $((PREV+1)) ]; then PREV=$N
    else RUNS="$RUNS $START-$PREV"; START=$N; PREV=$N; fi
  fi
done
[ -n "$START" ] && RUNS="$RUNS $START-$PREV"
if [ -z "$RUNS" ]; then echo "No existing bugs in $S-$E"; exit 1; fi
echo "Project $P, ranges that exist in Defects4J:$RUNS"
read -r -p "Run prompt + API + eval + summary for these ranges. API COSTS TOKENS. Continue? (y/N) " ANS
[ "$ANS" = "y" ] || { echo "cancelled"; exit 0; }
SC="/Users/suphawat/Documents/SQA/defect4j_test/scripts"
for R in $RUNS; do
  A=${R%-*}; B=${R#*-}
  echo "######## $P $A-$B"
  bash "$SC/run_project_claude.sh" "$P" prompt "$A" "$B" || { echo "prompt stage failed for $A-$B"; exit 1; }
  OUTF=$(mktemp)
  echo y | bash "$SC/auto_project_claude.sh" "$P" "$A" "$B" | tee "$OUTF"
  if grep -q '^STOP' "$OUTF"; then echo "Stopped by auto_project (see STOP line above). Remaining ranges not run."; exit 1; fi
done
echo "== all ranges done for $P"
