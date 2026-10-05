package org.apache.commons.lang3.text.translate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumericEntityUnescaperRegressionTest {

    @Test
    public void testUnfinishedEntity() {
        NumericEntityUnescaper neu = new NumericEntityUnescaper();
        String input = "Test &#x30 not test";
        String expected = "Test \u0030 not test";

        String result = neu.translate(input);
        assertEquals("Failed to support unfinished entities (i.e. missing semi-colon)", expected, result);
    }

    @Test
    public void testOutOfBounds() {
        NumericEntityUnescaper neu = new NumericEntityUnescaper();
        assertEquals("Failed to handle incomplete entity at end of string", "Test &", neu.translate("Test &"));
        assertEquals("Failed to handle incomplete entity at end of string", "Test &#", neu.translate("Test &#"));
        assertEquals("Failed to handle incomplete entity at end of string", "Test &#x", neu.translate("Test &#x"));
        assertEquals("Failed to handle incomplete entity at end of string", "Test &#X", neu.translate("Test &#X"));
    }
}