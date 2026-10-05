package org.apache.commons.codec.binary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

public class Base64InputStreamTest extends TestCase {

    public void testBase64EmptyInputStreamEncodeReturnsEofImmediately() throws Exception {
        byte[] crlf = new byte[] { 13, 10 };
        InputStream in = new ByteArrayInputStream(new byte[0]);
        in = new Base64InputStream(in, true, 76, crlf);

        assertEquals("EOF expected immediately for empty input", -1, in.read());
        assertEquals("Still EOF", -1, in.read());
    }
}