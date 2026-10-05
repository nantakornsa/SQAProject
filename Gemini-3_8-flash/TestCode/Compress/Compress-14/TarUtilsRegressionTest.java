package org.apache.commons.compress.archivers.tar;

import junit.framework.TestCase;
import org.junit.Test;

public class TarUtilsRegressionTest extends TestCase {

    @Test
    public void testParseOctalWithLeadingNullFollowedByNonZero() {
        // AIX native tar sometimes leaves fields starting with a NUL byte but containing
        // garbage/non-zero bytes in the remaining positions.
        // In the fixed version, if buffer[start] == 0, parseOctal immediately returns 0L.
        // In the buggy version, it checked whether ALL bytes were NUL, and if not, threw an IllegalArgumentException.
        byte[] buffer = new byte[] { 0, '1', '2', '3', 0 };
        long value = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, value);
    }
}