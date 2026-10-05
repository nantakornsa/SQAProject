package org.apache.commons.compress.archivers.zip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WinzipBackSlashWorkaroundTest {

    @Test
    public void testWinzipBackSlashWorkaround() {
        ZipArchiveEntry entry = new ZipArchiveEntry("test");
        entry.setPlatform(ZipArchiveEntry.PLATFORM_FAT);
        entry.setName("dir\\subdir\\file.txt");

        assertEquals("dir/subdir/file.txt", entry.getName());
    }
}