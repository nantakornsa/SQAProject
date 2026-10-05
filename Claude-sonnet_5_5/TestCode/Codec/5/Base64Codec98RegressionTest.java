package org.apache.commons.codec.binary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

/**
 * Regression test for CODEC-98: NPE when decoding unpadded input that ends
 * before the internal buffer has been allocated.
 */
public class Base64Codec98RegressionTest extends TestCase {

    public Base64Codec98RegressionTest(String name) {
        super(name);
    }

    public void testCodec98NPEInputStream() throws Exception {
        // "QQ" decodes to "A"; no padding and fewer than 4 chars, so the
        // internal buffer is never allocated before EOF is signalled.
        byte[] encoded = "QQ".getBytes("UTF-8");
        ByteArrayInputStream data = new ByteArrayInputStream(encoded);
        Base64InputStream stream = new Base64InputStream(data);

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = stream.read(buf)) != -1) {
            result.write(buf, 0, n);
        }
        stream.close();

        assertEquals("codec-98 NPE Base64InputStream", "A", new String(result.toByteArray(), "UTF-8"));
    }

    public void testCodec98NPEOutputStream() throws Exception {
        byte[] encoded = "QQ".getBytes("UTF-8");
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        Base64OutputStream stream = new Base64OutputStream(bytesOut, false);

        stream.write(encoded);
        stream.close();

        assertEquals("codec-98 NPE Base64OutputStream", "A", new String(bytesOut.toByteArray(), "UTF-8"));
    }
}