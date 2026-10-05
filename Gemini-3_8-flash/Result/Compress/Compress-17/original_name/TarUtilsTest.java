package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TarUtilsTest {

    @Test
    public void testParseOctalWithMultipleTrailingSpacesAndNulls() {
        byte[] buffer = new byte[] { '7', '7', ' ', ' ', 0 };
        long value = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(077L, value);
    }
}