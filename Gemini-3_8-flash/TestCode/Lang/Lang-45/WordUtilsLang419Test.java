package org.apache.commons.lang;

import junit.framework.TestCase;

/**
 * Regression test for LANG-419 (WordUtils.abbreviate with lower index greater than string length).
 */
public class WordUtilsLang419Test extends TestCase {

    public void testAbbreviateLowerGreaterThanLength() {
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }
}