package org.apache.commons.lang3;

import junit.framework.TestCase;

/**
 * Regression test for LANG-607 - StringUtils methods handling supplementary characters.
 */
public class StringUtilsSupplementaryCharsTest extends TestCase {

    private static final String CharU20000 = "\uD840\uDC00";
    private static final String CharU20001 = "\uD840\uDC01";

    public void testContainsNone_CharArrayWithSupplementaryChars() {
        // CharU20000 and CharU20001 share the same high surrogate (\uD840)
        // but have different low surrogates (\uDC00 vs \uDC01).
        // containsNone should not match on partial surrogate pairs.
        assertTrue(StringUtils.containsNone(CharU20000, CharU20001.toCharArray()));
        assertTrue(StringUtils.containsNone(CharU20001, CharU20000.toCharArray()));
    }

    public void testContainsNone_StringWithSupplementaryChars() {
        assertTrue(StringUtils.containsNone(CharU20000, CharU20001));
        assertTrue(StringUtils.containsNone(CharU20001, CharU20000));
    }

    public void testIndexOfAny_StringCharArrayWithSupplementaryChars() {
        assertEquals(2, StringUtils.indexOfAny("zz" + CharU20000, CharU20000.toCharArray()));
        assertEquals(-1, StringUtils.indexOfAny(CharU20000, CharU20001.toCharArray()));
        assertEquals(-1, StringUtils.indexOfAny(CharU20001, CharU20000.toCharArray()));
    }

    public void testIndexOfAny_StringStringWithSupplementaryChars() {
        assertEquals(2, StringUtils.indexOfAny("zz" + CharU20000, CharU20000));
        assertEquals(-1, StringUtils.indexOfAny(CharU20000, CharU20001));
        assertEquals(-1, StringUtils.indexOfAny(CharU20001, CharU20000));
    }
}