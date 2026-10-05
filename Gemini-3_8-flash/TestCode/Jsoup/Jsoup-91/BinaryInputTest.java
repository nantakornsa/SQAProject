package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BinaryInputTest {

    @Test
    public void testBinaryInputStreamThrowsException() {
        byte[] binaryData = new byte[100]; // 100 null bytes is detected as binary
        try {
            Jsoup.parse(new ByteArrayInputStream(binaryData), "UTF-8", "http://example.com");
            fail("Expected IOException on binary input");
        } catch (IOException e) {
            assertEquals("Input is binary and unsupported", e.getMessage());
        }
    }

    @Test
    public void testCharacterReaderDetectsBinary() {
        String binaryContent = "\0\0\0\0\0\0\0\0\0\0"; // 10 nulls threshold
        try {
            new CharacterReader(new StringReader(binaryContent));
            fail("Expected UncheckedIOException on binary input");
        } catch (UncheckedIOException e) {
            assertEquals("Input is binary and unsupported", e.getCause().getMessage());
        }
    }
}