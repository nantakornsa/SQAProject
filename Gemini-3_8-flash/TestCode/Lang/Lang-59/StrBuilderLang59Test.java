package org.apache.commons.lang.text;

import junit.framework.TestCase;

/**
 * Regression test for LANG-299 / Lang-59.
 * Tests that appendFixedWidthPadRight correctly truncates strings longer than width
 * without throwing an ArrayIndexOutOfBoundsException when buffer capacity is equal to width.
 */
public class StrBuilderLang59Test extends TestCase {

    public void testLang299() {
        StrBuilder sb = new StrBuilder(1);
        sb.appendFixedWidthPadRight("foo", 1, '-');
        assertEquals("f", sb.toString());
    }
}