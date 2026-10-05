package org.apache.commons.compress.archivers.sevenz;

import java.io.File;
import java.io.IOException;
import org.apache.commons.compress.AbstractTestCase;

public class SevenZOutputFileTest extends AbstractTestCase {

    public void testSevenEmptyFiles() throws Exception {
        testCompress252(7, 0);
    }

    public void testSixEmptyFiles() throws Exception {
        testCompress252(6, 0);
    }

    private void testCompress252(final int numberOfFiles, final int numberOfNonEmptyFiles)
        throws Exception {
        final int numberOfEmptyFiles = numberOfFiles - numberOfNonEmptyFiles;
        final File output = new File(dir, "COMPRESS-252-" + numberOfFiles + "-" + numberOfEmptyFiles + ".7z");
        SevenZOutputFile archive = new SevenZOutputFile(output);
        try {
            addFiles(archive, numberOfEmptyFiles, 0);
            addFiles(archive, numberOfNonEmptyFiles, 1);
            archive.finish();
        } finally {
            archive.close();
        }

        SevenZFile sevenZFile = new SevenZFile(output);
        try {
            SevenZArchiveEntry entry;
            int entries = 0;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                entries++;
            }
            assertEquals(numberOfFiles, entries);
        } finally {
            sevenZFile.close();
        }
    }

    private void addFiles(final SevenZOutputFile archive, final int count, final int size)
        throws IOException {
        for (int i = 0; i < count; i++) {
            SevenZArchiveEntry entry = new SevenZArchiveEntry();
            entry.setName("file-" + i + "-" + size);
            archive.putArchiveEntry(entry);
            if (size > 0) {
                archive.write(new byte[size]);
            }
            archive.closeArchiveEntry();
        }
    }
}