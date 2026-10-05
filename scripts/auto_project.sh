#!/bin/bash
P="$1"; S="$2"; E="$3"
if [ -z "$P" ] || [ -z "$S" ] || [ -z "$E" ]; then echo "Usage: bash auto_project.sh <Project> <Start> <End>"; exit 1; fi
BASE="/Users/suphawat/Documents/SQA/defect4j_test"
AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Gemini-3_8-flash"
RES="$AI/Result/$P"
SC="$BASE/scripts"
if [ "${#KKU_API_KEY}" != "67" ]; then echo "KKU_API_KEY length is ${#KKU_API_KEY}, expected 67. export it first."; exit 1; fi
mkdir -p "$RES"
TODO=""; SKIP=""
for N in $(seq "$S" "$E"); do
  T="$AI/TestCode/$P/$P-$N"; D="$RES/$P-$N"; R="$D/raw_response.txt"
  if ls "$T"/*.java > /dev/null 2>&1; then continue; fi
  if [ -s "$D/response.txt" ]; then SKIP="$SKIP $N(has_response)"; continue; fi
  if [ -s "$R" ] && grep -q '"stop_reason":"max_tokens"' "$R"; then SKIP="$SKIP $N(max_tokens_before)"; continue; fi
  TODO="$TODO $N"
done
echo "Project $P range $S-$E"
echo "Will call API for:${TODO:- none}"
echo "Skipped (no API call):${SKIP:- none}"
if [ -n "$TODO" ]; then
  echo "Each call costs tokens. No auto-retry. Stops on 401/429 or 2 failures in a row."
  read -r -p "Continue? (y/N) " ANS
  [ "$ANS" = "y" ] || { echo "cancelled"; exit 0; }
  export JAVA_HOME="/opt/homebrew/opt/openjdk@17"; export PATH="$JAVA_HOME/bin:$PATH"
  cd "$AI" || exit 1
  FAILS=0
  for N in $TODO; do
    echo "== API $P-$N"
    LOG="/tmp/auto_mvn_${P}_${N}.log"
    mvn -q compile exec:java -Dexec.mainClass="com.example.claudesonnet.ClaudeClient" -Dexec.args="$P $N $N" > "$LOG" 2>&1
    cat "$LOG" >> "$RES/api_run.log"
    if grep -qE 'HTTP[^0-9]*(401|429)|daily limit|rate limit|Unauthorized' "$LOG"; then
      echo "STOP: limit or auth error at $P-$N. See $LOG"; break
    fi
    R="$RES/$P-$N/raw_response.txt"
    if [ -s "$R" ] && grep -q '"stop_reason":"end_turn"' "$R"; then
      FAILS=0; echo "ok"
    elif [ -s "$R" ] && grep -q '"stop_reason":"max_tokens"' "$R"; then
      FAILS=0; echo "model hit max_tokens (not API error)"
    else
      FAILS=$((FAILS+1)); echo "no usable response ($FAILS in a row)"
      if [ "$FAILS" -ge 2 ]; then echo "STOP: 2 failures in a row. See $LOG"; break; fi
    fi
  done
fi
echo "== check responses (local, no tokens)"
bash "$SC/check_api.sh" "$P" "$S" "$E" extract
echo "== eval (local, no tokens)"
bash "$SC/run_project.sh" "$P" eval "$S" "$E"
echo "== compile errors to review"
for N in $(seq "$S" "$E"); do
  F="$RES/per_bug/$N.csv"; [ -f "$F" ] || continue
  CB=$(cut -d, -f4 "$F"); CF=$(cut -d, -f6 "$F")
  if [ "$CB" = "FAIL" ] || [ "$CF" = "FAIL" ]; then
    echo "-- $P-$N compile FAIL (buggy=$CB fixed=$CF)"
    for V in b f; do grep -m2 'error:' "$RES/final_logs/$P-$N$V.log" 2>/dev/null; done
  fi
done
echo "== summary (local, no tokens)"
bash "$SC/make_summary.sh" "$P"
