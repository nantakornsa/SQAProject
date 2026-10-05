package org.apache.commons.codec.binary;

import junit.framework.TestCase;

public class Base64Codec112RegressionTest extends TestCase {

    public Base64Codec112RegressionTest(String name) {
        super(name);
    }

    public void testCodec112() { // size calculation assumes always chunked
        byte[] in = new byte[] {0};
        byte[] out = Base64.encodeBase64(in);
        byte[] result = Base64.encodeBase64(in, false, false, out.length);
        assertEquals(out.length, result.length);
        assertEquals(new String(out, java.nio.charset.Charset.forName("UTF-8")),
                     new String(result, java.nio.charset.Charset.forName("UTF-8")));
    }

    public void testUnchunkedMaxSizeExact() {
        byte[] in = new byte[] {1, 2, 3, 4};
        byte[] out = Base64.encodeBase64(in);
        byte[] result = Base64.encodeBase64(in, false, false, out.length);
        assertEquals(out.length, result.length);
    }
}