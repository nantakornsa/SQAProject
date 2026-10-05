package org.apache.commons.compress.archivers.tar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import junit.framework.TestCase;

public class TarArchiveOutputStreamTest extends TestCase {

    public void testFinishWithUnclosedEntryThrowsException() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TarArchiveOutputStream tarOut = new TarArchiveOutputStream(out);
        TarArchiveEntry entry = new TarArchiveEntry("test.txt");
        entry.setSize(0);
        tarOut.putArchiveEntry(entry);
        try {
            tarOut.finish();
            fail("finish() should have thrown an IOException because an entry was not closed");
        } catch (IOException e) {
            // Expected exception
        } finally {
            try {
                tarOut.close();
            } catch (IOException ignored) {
            }
        }
    }
}