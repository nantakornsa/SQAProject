#!/bin/bash

export JAVA_HOME="/opt/homebrew/opt/openjdk@11"
export PATH="$JAVA_HOME/bin:$PATH"

D4J="/Users/suphawat/Documents/SQA/defect4j_test/defects4j/framework/bin/defects4j"
BASE_DIR="/Users/suphawat/Documents/SQA/defect4j_test"
CLAUDE_DIR="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Claude-sonnet_4_6"
RESULT_DIR="$CLAUDE_DIR/Result/Codec"
LOG_DIR="$RESULT_DIR/coverage_logs"
CSV="$RESULT_DIR/claude_coverage_results.csv"

mkdir -p "$LOG_DIR" /tmp/claude_suite
echo "bug,test_file,methods,methods_expected,methods_check,lines_total,lines_covered,conditions_total,conditions_covered,line_pct,cond_pct,status" > "$CSV"

# bug|test file in checkout (from git status)|expected method count (from TestCode)
MAP="1|src/test/org/apache/commons/codec/language/ClaudeCaverphoneTest.java|1
2|src/test/org/apache/commons/codec/binary/ClaudeBase64InputStreamTest.java|1
3|src/test/org/apache/commons/codec/language/DoubleMetaphoneCodec3Test.java|1
4|src/test/org/apache/commons/codec/binary/Base64Codec4Test.java|1
5|src/test/org/apache/commons/codec/binary/ClaudeBase64Codec98RegressionTest.java|2
6|src/test/org/apache/commons/codec/binary/Base64InputStreamCodec101RegressionTest.java|1
7|src/test/org/apache/commons/codec/binary/ClaudeBase64EncodeStringRegressionTest.java|3
8|src/test/org/apache/commons/codec/binary/ClaudeBase64InputStreamTest.java|2
9|src/test/org/apache/commons/codec/binary/ClaudeBase64Codec112RegressionTest.java|2
10|src/test/org/apache/commons/codec/language/ClaudeCaverphoneEndMbRegressionTest.java|1
11|src/test/java/org/apache/commons/codec/net/ClaudeQuotedPrintableCodecSoftLineBreakTest.java|3
12|src/test/java/org/apache/commons/codec/binary/BaseNCodecInputStreamCodec130Test.java|6
13|src/test/java/org/apache/commons/codec/language/ClaudeDoubleMetaphoneCodec184Test.java|2
14|src/test/java/org/apache/commons/codec/language/bm/ClaudePhoneticEngineLangRegressionTest.java|1
15|src/test/java/org/apache/commons/codec/language/ClaudeSoundexHWRuleRegressionTest.java|2
16|src/test/java/org/apache/commons/codec/binary/ClaudeBase32Codec200RegressionTest.java|2
17|src/test/java/org/apache/commons/codec/binary/ClaudeStringUtilsCodec229RegressionTest.java|2
18|src/test/java/org/apache/commons/codec/binary/ClaudeStringUtilsEqualsRegressionTest.java|4
"

while IFS='|' read -r BUG REL EXP; do
  [ -z "$BUG" ] && continue
  DIR="$BASE_DIR/Codec-${BUG}f"
  FILE="$DIR/$REL"
  NAME=$(basename "$FILE" .java)
  LOG="$LOG_DIR/Codec-${BUG}.log"

  if [ ! -f "$FILE" ]; then
    echo "${BUG},${NAME},,${EXP},,,,,,,,FILE_NOT_FOUND" >> "$CSV"
    echo "Codec-${BUG}: FILE_NOT_FOUND"
    continue
  fi

  METHODS=$(grep -c 'public void test' "$FILE")
  if [ "$METHODS" = "$EXP" ]; then CHECK="OK"; else CHECK="CHECK"; fi
  PKG=$(grep -m1 '^package ' "$FILE" | sed 's/package //; s/;.*//' | tr -d ' \r')
  PKG_DIR=$(echo "$PKG" | tr '.' '/')

  cd "$DIR" || continue
  rm -f summary.csv

  if [ "$BUG" = "1" ]; then
    "$D4J" coverage -t "${PKG}.${NAME}::testLocaleIndependence" > "$LOG" 2>&1
  else
    STAGE="/tmp/claude_suite/Codec-${BUG}"
    rm -rf "$STAGE"
    mkdir -p "$STAGE/$PKG_DIR"
    cp "$FILE" "$STAGE/$PKG_DIR/"
    tar -cjf "/tmp/claude_suite/Codec-${BUG}.tar.bz2" -C "$STAGE" .
    "$D4J" coverage -s "/tmp/claude_suite/Codec-${BUG}.tar.bz2" > "$LOG" 2>&1
  fi

  if [ -f summary.csv ]; then
    IFS=, read -r LT LC CT CC <<< "$(tail -n 1 summary.csv)"
    LP=$(awk -v a="$LC" -v b="$LT" 'BEGIN{ if (b>0) printf "%.1f", a/b*100; else print "0.0" }')
    CP=$(awk -v a="$CC" -v b="$CT" 'BEGIN{ if (b>0) printf "%.1f", a/b*100; else print "0.0" }')
    STATUS="OK"
  else
    LT=""; LC=""; CT=""; CC=""; LP=""; CP=""
    STATUS="FAILED"
  fi

  echo "${BUG},${NAME},${METHODS},${EXP},${CHECK},${LT},${LC},${CT},${CC},${LP},${CP},${STATUS}" >> "$CSV"
  echo "Codec-${BUG}: ${STATUS} | lines ${LC}/${LT} (${LP}%) | cond ${CC}/${CT} (${CP}%) | methods ${METHODS}/${EXP} ${CHECK}"
done <<< "$MAP"

echo ""
column -s, -t "$CSV"
