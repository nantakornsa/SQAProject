package org.apache.commons.lang3.text.translate;

import junit.framework.TestCase;

public class GeminiNumericEntityUnescaperTest extends TestCase {

    public void testSupplementaryUnescaping() {
        NumericEntityUnescaper neu = new NumericEntityUnescaper();
        String input = "&#68642;";
        String expected = "\uD803\uDC22";

        String result = neu.translate(input);
        assertEquals("Failed to unescape numeric entities supplementary characters", expected, result);
    }
}