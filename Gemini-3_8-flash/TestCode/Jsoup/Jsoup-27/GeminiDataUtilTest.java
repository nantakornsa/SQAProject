package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GeminiDataUtilTest {

    @Test
    public void testCharset() {
        assertEquals("utf-8", DataUtil.getCharsetFromContentType("text/html;charset=utf-8 "));
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html; charset=UTF-8"));
        assertEquals("ISO-8859-1", DataUtil.getCharsetFromContentType("text/html; charset=ISO-8859-1"));
        assertNull(DataUtil.getCharsetFromContentType("text/html"));
        assertNull(DataUtil.getCharsetFromContentType(null));
        assertNull(DataUtil.getCharsetFromContentType("text/html;charset=Unknown"));
    }

    @Test
    public void testQuotedCharset() {
        assertEquals("utf-8", DataUtil.getCharsetFromContentType("text/html; charset=\"utf-8\""));
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html; charset= \"UTF-8\""));
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset=\"Unsupported\""));
    }
}