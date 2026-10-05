package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TarUtilsCompress45Test {

    @Test
    public void testRoundTripOctalOrBinary8() {
        checkRoundTripOctalOrBinary(8);
    }

    private static void checkRoundTripOctalOrBinary(final int length) {
        final byte[] buffer = new byte[length];
        final long[] values = {
            0,
            1,
            TarConstants.MAXSIZE,
            -1,
            -72057594037927935L
        };

        for (final long value : values) {
            final boolean isOctal = value >= 0 && value <= TarUtils.computeMaxIdx(length);
            // computeMaxIdx for length 8 is 7^7 = 2097151
            // When not representable in octal, it uses binary encoding
            TarUtils.formatLongOctalOrBinaryBytes(value, buffer, 0, length);
            final long actual = TarUtils.parseOctalOrBinary(buffer, 0, length);
            assertEquals("Roundtrip failed for length " + length + " value " + value, value, actual);
        }
    }
}