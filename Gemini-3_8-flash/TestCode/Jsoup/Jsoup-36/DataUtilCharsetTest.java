package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;

public class DataUtilCharsetTest {

    @Test
    public void shouldCorrectCharsetForDuplicateCharsetString() {
        assertEquals("iso-8859-1", DataUtil.getCharsetFromContentType("text/html; charset=charset=iso-8859-1"));
    }

    @Test
    public void testQuotedCharset() {
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html; charset='UTF-8'"));
    }

    @Test
    public void shouldReturnNullForIllegalCharsetNames() {
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset=$HJKDF§$/("));
    }

    @Test
    public void shouldNotThrowExceptionOnEmptyCharset() {
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset="));
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset=;"));
    }
}