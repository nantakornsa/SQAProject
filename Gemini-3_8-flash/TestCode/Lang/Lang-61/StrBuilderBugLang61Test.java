package org.apache.commons.lang.text;

import junit.framework.TestCase;

public class StrBuilderBugLang61Test extends TestCase {

    public void testIndexOfLang294() {
        StrBuilder sb = new StrBuilder("onetwothree");
        sb.deleteFirst("three");
        assertEquals(-1, sb.indexOf("three"));
    }

    public void testLang294() {
        StrBuilder sb = new StrBuilder("onetwothree");
        sb.deleteFirst("three");
        assertEquals(-1, sb.indexOf("three", 0));
    }
}