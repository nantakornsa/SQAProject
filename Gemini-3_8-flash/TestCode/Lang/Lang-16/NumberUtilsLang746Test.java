package org.apache.commons.lang3.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Regression test for LANG-746: NumberUtils.createNumber does not handle uppercase hex prefixes (0X / -0X).
 */
public class NumberUtilsLang746Test {

    @Test
    public void testCreateNumberHexUpperCase() {
        assertEquals("createNumber(String) failed for 0Xfade", 
                Integer.valueOf(0xFADE), NumberUtils.createNumber("0Xfade"));
        assertEquals("createNumber(String) failed for 0XFADE", 
                Integer.valueOf(0xFADE), NumberUtils.createNumber("0XFADE"));
        assertEquals("createNumber(String) failed for -0Xfade", 
                Integer.valueOf(-0xFADE), NumberUtils.createNumber("-0Xfade"));
        assertEquals("createNumber(String) failed for -0XFADE", 
                Integer.valueOf(-0xFADE), NumberUtils.createNumber("-0XFADE"));
    }
}