package org.apache.commons.compress.archivers.tar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TarUtilsRegressionTest {

    @Test
    public void testParseOctalSpaceAndTrailingNull() {
        byte[] buffer = new byte[]{' ', 0};
        long value = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, value);
    }
}