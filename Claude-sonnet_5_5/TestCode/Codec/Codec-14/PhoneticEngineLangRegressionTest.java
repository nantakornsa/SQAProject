package org.apache.commons.codec.language.bm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Regression test for CODEC-187: language rules must be loaded per name type.
 */
public class PhoneticEngineLangRegressionTest {

    @Test
    public void testGenericBendzinUsesGenericLangRules() {
        final PhoneticEngine engine = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);

        final String result = engine.encode("Bendzin");

        assertEquals("bndzn|bntsn|bnzn|vndzn", result);
    }
}