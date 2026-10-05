package com.example.claudesonnet;

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
