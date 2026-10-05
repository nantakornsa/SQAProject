package org.apache.commons.lang3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilsEqualsRegressionTest {

    @Test
    public void testEqualsDifferentCharSequenceImplementations() {
        CharSequence string = "foo";
        CharSequence stringBuilder = new StringBuilder("foo");
        CharSequence stringBuffer = new StringBuffer("foo");

        // Buggy version delegates directly to cs1.equals(cs2),
        // which fails when comparing String to StringBuilder/StringBuffer
        assertTrue(StringUtils.equals(string, stringBuilder));
        assertTrue(StringUtils.equals(stringBuilder, string));
        assertTrue(StringUtils.equals(stringBuilder, stringBuffer));

        assertFalse(StringUtils.equals(string, new StringBuilder("bar")));
        assertFalse(StringUtils.equals(stringBuilder, new StringBuffer("bar")));
    }
}