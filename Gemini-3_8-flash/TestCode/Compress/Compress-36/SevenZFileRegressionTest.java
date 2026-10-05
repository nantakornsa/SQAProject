package org.apache.commons.compress.archivers.sevenz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class SevenZFileRegressionTest {

    @Test
    public void testReadEntryOfSize0() throws IOException {
        File emptyFile = File.createTempFile("empty", ".txt");
        File archiveFile = File.createTempFile("test", ".7z");
        try {
            SevenZOutputFile out = new SevenZOutputFile(archiveFile);
            try {
                SevenZArchiveEntry entry = out.createArchiveEntry(emptyFile, "empty.txt");
                out.putArchiveEntry(entry);
                out.closeArchiveEntry();
            } finally {
                out.close();
            }

            SevenZFile sevenZFile = new SevenZFile(archiveFile);
            try {
                SevenZArchiveEntry entry = sevenZFile.getNextEntry();
                assertNotNull(entry);
                assertEquals("empty.txt", entry.getName());
                assertEquals(0, entry.getSize());
                assertEquals(-1, sevenZFile.read());
            } finally {
                sevenZFile.close();
            }
        } finally {
            emptyFile.delete();
            archiveFile.delete();
        }
    }
}