package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EntitiesRegressionTest {

    @Test
    public void testUnescapeEntityWithNumbers() {
        String text = "&frac34;";
        String unescaped = Entities.unescape(text);
        assertEquals("¾", unescaped);
    }
}