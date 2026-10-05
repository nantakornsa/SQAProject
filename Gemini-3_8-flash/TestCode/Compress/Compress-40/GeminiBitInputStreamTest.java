package org.apache.commons.compress.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;
import org.junit.Test;

public class GeminiBitInputStreamTest {

    @Test
    public void testLittleEndianWithOverflow() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[] {
                87, // 01010111
                45, // 00101101
                66, // 01000010
                15, // 00001111
                90, // 01011010
                29, // 00011101
                88, // 01011000
                61, // 00111101
                33, // 00100001
                74  // 01001010
            });
        BitInputStream bin = new BitInputStream(in, ByteOrder.LITTLE_ENDIAN);
        assertEquals(23, // 10111
                     bin.readBits(5));
        assertEquals(714595605644185962L, // 0001-00111101-01011000-00011101-01011010-00001111-01000010-00101101-010
                     bin.readBits(63));
        assertEquals(1186, // 01001010-0010
                     bin.readBits(12));
        assertEquals(-1, bin.readBits(1));
        bin.close();
    }

    @Test
    public void testBigEndianWithOverflow() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[] {
                87, // 01010111
                45, // 00101101
                66, // 01000010
                15, // 00001111
                90, // 01011010
                29, // 00011101
                88, // 01011000
                61, // 00111101
                33, // 00100001
                74  // 01001010
            });
        BitInputStream bin = new BitInputStream(in, ByteOrder.BIG_ENDIAN);
        assertEquals(10, // 01010
                     bin.readBits(5));
        assertEquals(8274274654740644818L, // 111-00101101-01000010-00001111-01011010-00011101-01011000-00111101-00100
                     bin.readBits(63));
        assertEquals(330, // 001-01001010
                     bin.readBits(11));
        assertEquals(-1, bin.readBits(1));
        bin.close();
    }
}