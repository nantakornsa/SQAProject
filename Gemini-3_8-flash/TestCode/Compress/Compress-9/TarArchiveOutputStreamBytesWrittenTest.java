package org.apache.commons.compress.archivers.tar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

public class TarArchiveOutputStreamBytesWrittenTest extends TestCase {

    public void testBytesWrittenMatchesTotalOutputLength() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TarArchiveOutputStream tarOut = new TarArchiveOutputStream(bos);

        TarArchiveEntry entry = new TarArchiveEntry("test.txt");
        byte[] content = "Hello, world!".getBytes("UTF-8");
        entry.setSize(content.length);

        tarOut.putArchiveEntry(entry);
        tarOut.write(content);
        tarOut.closeArchiveEntry();
        tarOut.close();

        byte[] archiveBytes = bos.toByteArray();
        assertEquals(archiveBytes.length, tarOut.getBytesWritten());
        assertEquals(archiveBytes.length, tarOut.getCount());
    }
}