package org.apache.commons.compress.archivers.tar;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.fail;

public class TarArchiveInputStreamTest {

    @Test
    public void testThrowsExceptionOnTruncatedEntry() throws Exception {
        TarArchiveEntry entry = new TarArchiveEntry("test.txt");
        entry.setSize(100);
        byte[] headerBuf = new byte[512];
        entry.writeEntryHeader(headerBuf);

        // Stream contains only the header but no data content
        ByteArrayInputStream in = new ByteArrayInputStream(headerBuf);
        TarArchiveInputStream tais = new TarArchiveInputStream(in);

        TarArchiveEntry readEntry = tais.getNextTarEntry();
        if (readEntry == null) {
            fail("Expected a tar entry");
        }

        byte[] buf = new byte[100];
        try {
            tais.read(buf, 0, buf.length);
            fail("Expected IOException on truncated entry");
        } catch (IOException e) {
            // Expected
        } finally {
            tais.close();
        }
    }
}