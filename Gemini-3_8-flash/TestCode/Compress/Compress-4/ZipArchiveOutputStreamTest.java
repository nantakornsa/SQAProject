package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import junit.framework.TestCase;

public class ZipArchiveOutputStreamTest extends TestCase {

    public void testFinishAndCloseDoesNotCorruptArchive() throws IOException {
        File testArchive = File.createTempFile("compress-test", ".zip");
        ZipArchiveOutputStream out = null;
        ZipFile zf = null;
        try {
            out = new ZipArchiveOutputStream(new FileOutputStream(testArchive));
            ZipArchiveEntry entry = new ZipArchiveEntry("test.txt");
            out.putArchiveEntry(entry);
            out.write("hello".getBytes("US-ASCII"));
            out.closeArchiveEntry();
            out.finish();
            out.close();
            out = null;

            zf = new ZipFile(testArchive);
            assertNotNull(zf.getEntry("test.txt"));
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            ZipFile.closeQuietly(zf);
            if (testArchive.exists()) {
                testArchive.delete();
            }
        }
    }
}