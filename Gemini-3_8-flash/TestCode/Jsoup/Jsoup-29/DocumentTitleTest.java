package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentTitleTest {

    @Test
    public void testTitleNormalisesWhitespace() {
        Document doc = Jsoup.parse("<title>   Hello\nthere   \n   now   \n");
        assertEquals("Hello there now", doc.title());
    }
}