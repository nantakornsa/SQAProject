package org.apache.commons.lang;

import junit.framework.TestCase;

public class Lang480Test extends TestCase {

    public void testEscapeHtmlHighUnicode() throws java.io.UnsupportedEncodingException {
        // UTF-8 representation of U+1D362 (COUNTING ROD UNIT DIGIT THREE, decimal 119650)
        byte[] data = new byte[] { (byte) 0xF0, (byte) 0x9D, (byte) 0x8D, (byte) 0xA2 };
        String input = new String(data, "UTF-8");

        String escaped = StringEscapeUtils.escapeHtml(input);
        assertEquals("High unicode was not escaped correctly", "&#119650;", escaped);
    }
}