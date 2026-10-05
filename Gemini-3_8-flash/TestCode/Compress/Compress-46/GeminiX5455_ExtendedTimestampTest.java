package org.apache.commons.compress.archivers.zip;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.fail;

public class GeminiX5455_ExtendedTimestampTest {

    @Test
    public void testUnixTimeLimits() {
        final X5455_ExtendedTimestamp xf = new X5455_ExtendedTimestamp();

        // Signed 32-bit integer upper bound: Integer.MAX_VALUE (2147483647L)
        // Values > Integer.MAX_VALUE should throw IllegalArgumentException
        try {
            xf.setModifyJavaTime(new Date((Integer.MAX_VALUE + 1L) * 1000L));
            fail("Expected IllegalArgumentException for unix time > Integer.MAX_VALUE");
        } catch (final IllegalArgumentException expected) {
            // Expected
        }

        // Signed 32-bit integer lower bound: Integer.MIN_VALUE (-2147483648L)
        // Values < Integer.MIN_VALUE should throw IllegalArgumentException
        try {
            xf.setModifyJavaTime(new Date((Integer.MIN_VALUE - 1L) * 1000L));
            fail("Expected IllegalArgumentException for unix time < Integer.MIN_VALUE");
        } catch (final IllegalArgumentException expected) {
            // Expected
        }
    }
}