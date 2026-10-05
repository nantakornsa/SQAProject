package org.apache.commons.compress.archivers.zip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class X7875_NewUnixRegressionTest {

    @Test
    public void testGetCentralDirectoryLengthIsZero() throws Exception {
        X7875_NewUnix xf = new X7875_NewUnix();
        xf.setUID(1000);
        xf.setGID(1000);

        // According to Info-ZIP specification, the central directory data length
        // for extra field 0x7875 must be 0, regardless of the local file data length.
        assertEquals(new ZipShort(0), xf.getCentralDirectoryLength());
        assertEquals(0, xf.getCentralDirectoryLength().getValue());
    }
}