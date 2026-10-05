package org.apache.commons.codec.binary;

import junit.framework.TestCase;

/**
 * Regression test for CODEC-99: Base64.encodeBase64String must not chunk its output.
 */
public class Base64EncodeStringRegressionTest extends TestCase {

    public void testEncodeBase64StringIsNotChunked() {
        byte[] data = StringUtils.getBytesUtf8("Hello World");
        assertEquals("SGVsbG8gV29ybGQ=", Base64.encodeBase64String(data));
    }

    public void testEncodeBase64StringSingleByte() {
        byte[] data = StringUtils.getBytesUtf8("f");
        assertEquals("Zg==", Base64.encodeBase64String(data));
    }

    public void testEncodeBase64StringLongInputHasNoLineBreaks() {
        byte[] data = new byte[200];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        String result = Base64.encodeBase64String(data);
        assertFalse("Result must not contain CR", result.indexOf('\r') >= 0);
        assertFalse("Result must not contain LF", result.indexOf('\n') >= 0);
    }
}