package org.apache.commons.compress.archivers.zip;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZipArchiveEntryTest {

    @Test
    public void testIsUnixSymlinkIsFalseIfMoreThanOneFlagIsSet() {
        ZipArchiveEntry ze = new ZipArchiveEntry("foo");
        ze.setUnixMode(UnixStat.LINK_FLAG);
        assertTrue(ze.isUnixSymlink());

        ze.setUnixMode(UnixStat.LINK_FLAG | UnixStat.DIR_FLAG);
        assertFalse(ze.isUnixSymlink());
    }
}