package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for CODEC-231: StringUtils.equals(CharSequence, CharSequence)
 * throws StringIndexOutOfBoundsException when the CharSequences differ in length.
 */
public class StringUtilsEqualsRegressionTest {

    @Test
    public void testEqualsCharSequenceShorterFirst() {
        Assert.assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
    }

    @Test
    public void testEqualsCharSequenceLongerFirst() {
        Assert.assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
    }

    @Test
    public void testEqualsCharSequenceMixedTypesDifferentLength() {
        Assert.assertFalse(StringUtils.equals("abc", new StringBuilder("abcd")));
        Assert.assertFalse(StringUtils.equals(new StringBuilder("abcd"), "abc"));
    }

    @Test
    public void testEqualsCharSequenceSameContent() {
        Assert.assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));
        Assert.assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }
}