package org.apache.commons.codec.binary;

import java.util.Arrays;

import junit.framework.TestCase;

public class Base64Codec4Test extends TestCase {

    public void testEncodeSingleByteRegression() {
        Base64 base64 = new Base64();
        byte[] expected = new byte[] {'u', 'A', '=', '='};
        byte[] actual = base64.encode(new byte[] {-72});
        assertTrue("Expected uA== but got: " + new String(actual)
                + " (length " + actual.length + ")",
                Arrays.equals(expected, actual));
    }
}