package org.jsoup.helper;

import org.jsoup.parser.Parser;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DataUtilTest {

    @Test
    public void handlesUncheckedIOExceptionDuringParseRead() {
        // Prepare an input stream that returns valid HTML for the initial buffer read (first 1024 bytes),
        // and then throws an IOException when the parser reads further from the stream.
        byte[] initialBytes = new byte[2048];
        for (int i = 0; i < initialBytes.length; i++) {
            initialBytes[i] = 'a';
        }

        InputStream validStream = new ByteArrayInputStream(initialBytes);
        InputStream errorStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Stream interrupted");
            }
        };

        InputStream combined = new SequenceInputStream(validStream, errorStream);

        try {
            DataUtil.load(combined, "UTF-8", "http://example.com", Parser.htmlParser());
            fail("Expected IOException was not thrown");
        } catch (org.jsoup.UncheckedIOException e) {
            fail("Expected IOException, but caught UncheckedIOException");
        } catch (IOException e) {
            assertEquals("Stream interrupted", e.getMessage());
        }
    }
}