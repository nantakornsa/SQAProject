package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TarUtilsRegressionTest {

    @Test
    public void testParseOctalWithoutTrailingSpaceOrNul() throws Exception {
        final long MAX_OCTAL_OVERFLOW = 0777777777777L; // 12 octal digits without trailing space or NUL
        final String maxOctal = "777777777777";
        byte[] buffer = maxOctal.getBytes("UTF-8");
        long value = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(MAX_OCTAL_OVERFLOW, value);
    }
}