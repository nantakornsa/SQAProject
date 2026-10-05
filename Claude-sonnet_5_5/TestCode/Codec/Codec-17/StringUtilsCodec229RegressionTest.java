package org.apache.commons.codec.binary;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * Regression test for CODEC-229: newStringIso8859_1(null) should return null
 * instead of throwing a NullPointerException.
 */
public class StringUtilsCodec229RegressionTest extends TestCase {

    public void testNewStringIso8859_1NullInput_CODEC229() {
        Assert.assertNull(StringUtils.newStringIso8859_1(null));
    }

    public void testNewStringNullInput_CODEC229() {
        Assert.assertNull(StringUtils.newStringUtf8(null));
        Assert.assertNull(StringUtils.newStringIso8859_1(null));
        Assert.assertNull(StringUtils.newStringUsAscii(null));
        Assert.assertNull(StringUtils.newStringUtf16(null));
        Assert.assertNull(StringUtils.newStringUtf16Be(null));
        Assert.assertNull(StringUtils.newStringUtf16Le(null));
    }
}