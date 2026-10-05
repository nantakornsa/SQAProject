package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementHtmlNotPrettyTest {

    @Test
    public void testHtmlPreservesWhitespaceWhenNotPretty() {
        Document doc = Jsoup.parse("<div>   \n<p>Hello\n there\n</p></div>");
        doc.outputSettings().prettyPrint(false);

        Element div = doc.select("div").first();
        assertEquals("   \n<p>Hello\n there\n</p>", div.html());
    }
}