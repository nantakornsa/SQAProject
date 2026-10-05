package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementRegressionTest {

    @Test
    public void testParentlessToString() {
        Document doc = Jsoup.parse("<img src='foo'>");
        Element img = doc.select("img").first();
        assertEquals("\n<img src=\"foo\" />", img.toString());

        img.remove(); // removes node from its parent and document
        assertEquals("<img src=\"foo\" />", img.toString());
    }
}