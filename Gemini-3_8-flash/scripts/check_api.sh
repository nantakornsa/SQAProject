#!/bin/bash
P="$1"; S="$2"; E="$3"; MODE="$4"
if [ -z "$P" ] || [ -z "$S" ] || [ -z "$E" ]; then echo "Usage: bash check_api.sh <Project> <Start> <End> [extract]"; exit 1; fi
AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Gemini-3_8-flash"
RES="$AI/Result/$P"
OUT="$RES/api_problems.txt"
: > "$OUT"
for N in $(seq "$S" "$E"); do
  D="$RES/$P-$N"; R="$D/raw_response.txt"; T="$AI/TestCode/$P/$P-$N"
  if ls "$T"/*.java > /dev/null 2>&1; then continue; fi
  OT="?"; SR=""
  if [ ! -s "$R" ]; then
    WHY="no_raw_response"
  else
    SR=$(grep -oE '"stop_reason":"[a-z_]+"' "$R" | tail -1 | cut -d'"' -f4)
    OT=$(grep -oE '"output_tokens":[0-9]+' "$R" | tail -1 | cut -d: -f2)
    if [ "$SR" = "end_turn" ]; then
      if [ -s "$D/response.txt" ]; then WHY="end_turn_code_not_extracted"; else WHY="end_turn_no_response_txt"; fi
    else
      WHY="incomplete_stop_reason_${SR:-none}"
    fi
  fi
  echo "$P-$N $WHY output_tokens=$OT" | tee -a "$OUT"
  if [ "$MODE" = "extract" ] && [ "$WHY" = "end_turn_code_not_extracted" ]; then
    TMP="/tmp/extract_$P-$N.java"
    perl -0ne 'if (/```java\s*\n(.*?)```/s) { print $1 }' "$D/response.txt" > "$TMP"
    CL=$(grep -m1 -oE 'public (final )?class [A-Za-z0-9_]+' "$TMP" | awk '{print $NF}')
    if [ -s "$TMP" ] && [ -n "$CL" ]; then
      mkdir -p "$T"; cp "$TMP" "$T/$CL.java"; echo "  extracted -> $T/$CL.java"
    else
      echo "  could not extract (no java fence or no public class). nothing written"
    fi
  fi
done
echo "problems listed: $(grep -c . "$OUT")  (file: $OUT)"
