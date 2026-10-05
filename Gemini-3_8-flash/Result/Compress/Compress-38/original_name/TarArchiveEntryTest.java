package org.apache.commons.compress.archivers.tar;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TarArchiveEntryTest {

    @Test
    public void testPaxHeaderWithNameEndingInSlashIsNotDirectory() {
        TarArchiveEntry paxHeaderEntry = new TarArchiveEntry("PaxHeader/", TarConstants.LF_PAX_EXTENDED_HEADER_LC);
        assertFalse("Pax header ending with slash should not be considered a directory", paxHeaderEntry.isDirectory());

        TarArchiveEntry globalPaxHeaderEntry = new TarArchiveEntry("GlobalPaxHeader/", TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER);
        assertFalse("Global pax header ending with slash should not be considered a directory", globalPaxHeaderEntry.isDirectory());
    }
}