package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectorRegressionTest {

    @Test
    public void testAttributeWithBracketsInQuotes() {
        String html = "<div data='End]'>One</div> <div data='[Another)]]'>Two</div>";
        Document doc = Jsoup.parse(html);

        assertEquals("One", doc.select("div[data='End]']").first().text());
        assertEquals("Two", doc.select("div[data='[Another)]]']").first().text());
    }
}