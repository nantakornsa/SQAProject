package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;

public class EntitiesTest {

    @Test
    public void testQuoteReplacementsInUnescape() {
        String escaped = "&#92; &#36;";
        String unescaped = "\\ $";
        assertEquals(unescaped, Entities.unescape(escaped));
    }
}