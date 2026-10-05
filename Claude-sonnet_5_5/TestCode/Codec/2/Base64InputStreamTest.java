package org.apache.commons.codec.binary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class Base64InputStreamTest extends TestCase {

    public void testBase64EmptyInputStreamEncodeReturnsEofImmediately() throws Exception {
        byte[] crlf = new byte[] { 13, 10 };
        byte[] decoded = new byte[0];

        InputStream in = new Base64InputStream(new ByteArrayInputStream(decoded), true, 76, crlf);

        assertEquals("EOF", -1, in.read());
        assertEquals("Still EOF", -1, in.read());

        byte[] chunk = new byte[10];
        assertEquals("Still EOF on bulk read", -1, in.read(chunk, 0, chunk.length));
    }
}