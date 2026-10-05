package org.apache.commons.compress.archivers.tar;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TarArchiveInputStreamTest {

    @Test
    public void testParsePaxHeadersWithBlankLines() throws IOException {
        final TarArchiveInputStream is = new TarArchiveInputStream(new ByteArrayInputStream(new byte[0]));
        final Map<String, String> headers = is.parsePaxHeaders(
                new ByteArrayInputStream("\n\n11 foo=bar\n\n".getBytes("UTF-8"))
        );
        assertEquals(1, headers.size());
        assertEquals("bar", headers.get("foo"));
    }
}