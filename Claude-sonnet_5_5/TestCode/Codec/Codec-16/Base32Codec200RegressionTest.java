package org.apache.commons.codec.binary;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class Base32Codec200RegressionTest {

    @Test
    public void testHexAlphabetAllowsWAsPad() {
        final Base32 codec = new Base32(true, (byte) 'W'); // 'W' is not in the hex alphabet, so it should be allowed
        assertNotNull(codec);
    }

    @Test
    public void testHexAlphabetWithWPadRoundTrip() {
        final Base32 codec = new Base32(0, null, true, (byte) 'W');
        final byte[] data = "foob".getBytes();
        final byte[] encoded = codec.encode(data);
        assertEquals("CPNMUOGW", new String(encoded));
        assertArrayEquals(data, codec.decode(encoded));
    }
}