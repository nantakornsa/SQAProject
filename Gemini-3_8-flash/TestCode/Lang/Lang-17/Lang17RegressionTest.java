package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Lang17RegressionTest {

    @Test
    public void testLang720() {
        String input = new StringBuilder("\ud842\udfb7").append("A").toString();
        String escaped = StringEscapeUtils.escapeXml(input);
        assertEquals(input, escaped);
    }
}