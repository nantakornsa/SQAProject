#!/bin/bash
P="$1"
if [ -z "$P" ]; then echo "Usage: bash make_summary.sh <Project>"; exit 1; fi
AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Gemini-3_8-flash"
RES="$AI/Result/$P"
MERGED="$RES/gemini_auto_merged.csv"
UNM="$RES/unmeasurable.txt"
OUT="$RES/Gemini_${P}_Summary.md"
INCF="/tmp/inc_$P.txt"
if [ ! -f "$MERGED" ]; then echo "No $MERGED - run the eval stage first"; exit 1; fi
touch "$UNM"
: > "$INCF"
for N in $(awk -F, 'NR>1 && $15 ~ /NO_TEST/ {print $1}' "$MERGED"); do
  R="$RES/$P-$N/raw_response.txt"
  if [ -s "$R" ] && grep -q '"stop_reason":"max_tokens"' "$R"; then echo "$N" >> "$INCF"; fi
done
awk -F, -v UF="$UNM" -v INCF="$INCF" -v PROJ="$P" '
function ad(s, v) { return s (s == "" ? "" : ", ") v }
BEGIN {
  while ((getline x < UF) > 0) { gsub(/[ \r]/, "", x); if (x != "") U[x] = 1 }
  while ((getline y < INCF) > 0) { gsub(/[ \r]/, "", y); if (y != "") I[y] = 1 }
  print "Gemini " PROJ " Summary"
  print ""
  print "Model: gemini-3.8-flash per config"
  print ""
  print "| Bug | Detected | Line % | Cond % | Note |"
  print "|---|---|---|---|---|"
}
NR > 1 {
  b = $1; l = $11; c = $14
  if (l == "") l = "-"
  if (c == "") c = "-"
  det = "-"; n = ""
  if (b in U) { cat = "U"; n = "not measurable (excluded from FDR)" }
  else if (b in I) { cat = "I"; n = "model did not finish (max_tokens)" }
  else if ($15 ~ /NO_TEST|CHECKOUT_FAILED|COLLISION/) { cat = "O"; n = $15 }
  else if ($8 == "YES") { cat = "D"; det = "YES" }
  else {
    cat = "N"; det = "NO"
    if ($4 != "PASS" || $6 != "PASS") { n = "compile error, check log"; ce = ad(ce, b) }
    else if ($5 == "PASS" && $7 == "PASS") { n = "pass on both"; pb = ad(pb, b) }
    else { n = "fail on both"; fb = ad(fb, b) }
  }
  if ((cat == "D" || cat == "N") && $15 != "") n = (n == "" ? $15 : n "; " $15)
  cnt[cat]++
  L[cat] = ad(L[cat], b)
  if ((cat == "D" || cat == "N") && $11 != "") { k++; sl += $11; sc += $14 }
  print "| " b " | " det " | " l " | " c " | " n " |"
}
END {
  d = cnt["D"] + 0; n2 = cnt["N"] + 0; u = cnt["U"] + 0; i = cnt["I"] + 0; o = cnt["O"] + 0; m = d + n2
  print ""
  print "Summary"
  printf "Total bugs in file: %d\n", NR - 1
  printf "Detected %d | Not detected %d | Not measurable %d | Model did not finish %d | No test or checkout problem %d\n", d, n2, u, i, o
  if (m > 0) printf "FDR on measured bugs: %d of %d = %.2f percent\n", d, m, d / m * 100
  if (m + u > 0) printf "FDR if not measurable counted as misses: %d of %d = %.2f percent\n", d, m + u, d / (m + u) * 100
  if (m + u + i > 0) printf "FDR if not measurable and unfinished counted as misses: %d of %d = %.2f percent\n", d, m + u + i, d / (m + u + i) * 100
  if (k > 0) printf "Avg line coverage %.1f percent and avg cond coverage %.1f percent, from %d measured bugs with coverage\n", sl / k, sc / k, k
  print "Fault detected means the test fails on buggy and passes on fixed, with both versions compiling."
  print "TODO: check compile errors in logs. Errors in the test itself count as not detected. Errors from helper classes or the script are not measurable: list them in unmeasurable.txt."
  print ""
  print "Not detected"
  print "Compile error, check log: " ce
  print "Pass on both buggy and fixed: " pb
  print "Fail on both: " fb
  print ""
  print "Reported separately"
  print "Not measurable: " L["U"]
  print "Model did not finish (max_tokens): " L["I"]
  print "No test, checkout failed or name collision: " L["O"]
  print ""
  print "Notes"
  print "Tests not edited or regenerated. Renamed tests (prefix Gemini) keep originals in original_name."
  print "Coverage is measured on the fixed version, only on classes touched by the bug fix, so it is low by design."
  print "Coverage averages exclude not measurable bugs and bugs with no coverage data. A 0.0 percent row can still mean no data: check lines_total in the csv."
  print ""
  print "Method"
  print "Prompts carry an extra EFFICIENCY NOTE that the Claude prompts do not have, because Gemini thinking tokens consumed max_tokens."
  print "max_tokens 4096 for both models, Gemini thinking tokens count inside it."
  print "Measured with Defects4J on Java 11 using gemini_eval2.sh. Claude and Gemini ran different projects so they cannot be compared directly."
}' "$MERGED" > "$OUT"
echo "written: $OUT"
