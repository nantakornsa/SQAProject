package org.apache.commons.compress.archivers.ar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import junit.framework.TestCase;

public class ArArchiveInputStreamTest extends TestCase {

    public void testReadMultiEntries() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArArchiveOutputStream aos = new ArArchiveOutputStream(baos);

        byte[] content1 = "foo".getBytes("US-ASCII");
        byte[] content2 = "bar".getBytes("US-ASCII");

        ArArchiveEntry entry1 = new ArArchiveEntry("foo.txt", content1.length);
        aos.putArchiveEntry(entry1);
        aos.write(content1);
        aos.closeArchiveEntry();

        ArArchiveEntry entry2 = new ArArchiveEntry("bar.txt", content2.length);
        aos.putArchiveEntry(entry2);
        aos.write(content2);
        aos.closeArchiveEntry();
        aos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ArArchiveInputStream ais = new ArArchiveInputStream(bais);

        ArArchiveEntry readEntry1 = ais.getNextArEntry();
        assertNotNull(readEntry1);
        assertEquals("foo.txt", readEntry1.getName());

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int read;
        while ((read = ais.read(buf)) > 0) {
            out1.write(buf, 0, read);
        }
        assertEquals("foo", new String(out1.toByteArray(), "US-ASCII"));

        ArArchiveEntry readEntry2 = ais.getNextArEntry();
        assertNotNull("Second entry should not be null", readEntry2);
        assertEquals("bar.txt", readEntry2.getName());

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        while ((read = ais.read(buf)) > 0) {
            out2.write(buf, 0, read);
        }
        assertEquals("bar", new String(out2.toByteArray(), "US-ASCII"));

        assertNull(ais.getNextArEntry());
        ais.close();
    }
}