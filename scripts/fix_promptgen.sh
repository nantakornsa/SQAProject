#!/bin/bash
# Usage: bash fix_promptgen.sh [Project Start End]
# Patches PromptGenerator.java:
#   1. Files.readString / Files.readAllLines (single argument, may span lines) read UTF-8 first,
#      then fall back to ISO-8859-1, so test files with odd encoding no longer fail
#      with "Input length = 1".
#   2. test file lookup also accepts paths containing /tests/ (needed for Chart).
# Backs up (*.bak_enc), compiles, rolls back on compile failure.
# If Project Start End are given, re-runs the prompt stage afterwards.

AI="/Users/suphawat/Documents/SQA/FinalProject-Test/SQAProject/Gemini-3_8-flash"
SCR="/Users/suphawat/Documents/SQA/defect4j_test/scripts"
export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
export PATH="$JAVA_HOME/bin:$PATH"

PG=$(find "$AI/src" -name PromptGenerator.java | head -1)
[ -z "$PG" ] && { echo "PromptGenerator.java not found under $AI/src"; exit 1; }
DIR=$(dirname "$PG")
IO="$DIR/GeminiIo.java"
CREATED_IO=0

[ -f "$PG.bak_enc" ] || cp "$PG" "$PG.bak_enc"

echo "== before"
grep -nE 'Files\.(readString|readAllLines)\(|GeminiIo\.|"/tests/"' "$PG"

if [ ! -f "$IO" ]; then
  PKG=$(grep -m1 '^package ' "$PG")
  {
    echo "$PKG"
    cat <<'EOF'

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

final class GeminiIo {
    static String readString(Path p) throws IOException {
        byte[] b = Files.readAllBytes(p);
        try {
            return StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(b)).toString();
        } catch (CharacterCodingException e) {
            return new String(b, StandardCharsets.ISO_8859_1);
        }
    }

    static List<String> readAllLines(Path p) throws IOException {
        return new BufferedReader(new StringReader(readString(p))).lines().collect(Collectors.toList());
    }
}
EOF
  } > "$IO"
  CREATED_IO=1
fi

perl -0pi -e 's/Files\.(readString|readAllLines)\(((?:[^(),]|\((?:[^()]|\([^()]*\))*\))+)\)/GeminiIo.$1($2)/g' "$PG"

if ! grep -q '"/tests/"' "$PG"; then
  perl -0pi -e 's/\.contains\("\/test\/"\)/.contains("\/test\/") || path.toString().contains("\/tests\/")/' "$PG"
fi

echo "== after"
grep -nE 'Files\.(readString|readAllLines)\(|GeminiIo\.|"/tests/"' "$PG"

echo "== compile"
cd "$AI" || exit 1
if ! mvn -q compile; then
  echo "COMPILE FAILED - rolling back"
  cp "$PG.bak_enc" "$PG"
  [ "$CREATED_IO" = "1" ] && rm -f "$IO"
  exit 1
fi
echo "compile OK (original saved as $PG.bak_enc)"

if [ -n "$1" ] && [ -n "$2" ] && [ -n "$3" ]; then
  bash "$SCR/run_project.sh" "$1" prompt "$2" "$3"
fi
