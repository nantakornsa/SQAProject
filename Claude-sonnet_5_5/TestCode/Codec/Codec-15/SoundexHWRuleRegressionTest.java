package org.apache.commons.codec.language;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for CODEC-199: the Soundex H/W rule must look back past
 * consecutive H and W characters, not only the single preceding one.
 */
public class SoundexHWRuleRegressionTest {

    @Test
    public void testHWRuleConsecutiveHW() {
        final Soundex soundex = new Soundex();
        // "yhwdyt": Y, then H and W (ignored), D -> 3, Y (vowel-like, no code), T -> 3.
        // The first letter Y has code 0, so it does not match D and D must be coded.
        Assert.assertEquals("Y330", soundex.encode("yhwdyt"));
        Assert.assertEquals("Y330", soundex.soundex("yhwdyt"));
    }

    @Test
    public void testHWRuleExamples() {
        final Soundex soundex = new Soundex();
        Assert.assertEquals("A261", soundex.encode("Ashcraft"));
        Assert.assertEquals("A261", soundex.encode("Ashcroft"));
        Assert.assertEquals("Y330", soundex.encode("yehudit"));
        Assert.assertEquals("Y330", soundex.encode("yhwdyt"));
    }
}