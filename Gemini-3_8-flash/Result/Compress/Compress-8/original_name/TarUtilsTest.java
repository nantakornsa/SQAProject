package org.apache.commons.compress.archivers.tar;

import junit.framework.TestCase;

public class TarUtilsTest extends TestCase {

    public void testParseOctalInvalidLength() {
        byte[] buffer = new byte[0];
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException - should be at least 2 bytes long");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        buffer = new byte[]{0};
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException - should be at least 2 bytes long");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public void testParseOctalInvalidTrailer() {
        byte[] buffer = new byte[]{'7', '7', '7'};
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException - must have trailing NUL or space");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public void testParseOctalInvalidEmbeddedSpace() throws Exception {
        byte[] buffer = " 0 07 ".getBytes("UTF-8");
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException - embedded space");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}