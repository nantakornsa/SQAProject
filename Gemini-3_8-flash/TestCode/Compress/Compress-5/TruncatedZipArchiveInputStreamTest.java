package org.apache.commons.compress.archivers.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import junit.framework.TestCase;

public class TruncatedZipArchiveInputStreamTest extends TestCase {

    public void testReadTruncatedEntryThrowsIOException() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry("test.txt");
        entry.setMethod(ZipEntry.DEFLATED);
        zos.putNextEntry(entry);
        byte[] data = new byte[1024];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i % 256);
        }
        zos.write(data);
        zos.closeEntry();
        zos.close();

        byte[] fullZip = baos.toByteArray();
        // Truncate the ZIP file in the middle of the compressed data payload
        byte[] truncatedZip = new byte[fullZip.length - 100];
        System.arraycopy(fullZip, 0, truncatedZip, 0, truncatedZip.length);

        ZipArchiveInputStream in = new ZipArchiveInputStream(new ByteArrayInputStream(truncatedZip));
        assertNotNull(in.getNextZipEntry());

        byte[] buf = new byte[512];
        try {
            while (in.read(buf, 0, buf.length) > 0) {
                // consume available bytes until truncation
            }
            fail("shouldn't be able to read from truncated entry");
        } catch (IOException e) {
            // Expected on fixed version
        } finally {
            in.close();
        }
    }
}