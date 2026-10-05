package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectorRegressionTest {

    @Test
    public void testNotSelector() {
        Document doc = Jsoup.parse("<p>Two</p> <p><span>Three</span></p>");

        Elements el1 = doc.body().select(":not(p)"); // should just be body and span
        assertEquals(2, el1.size());
        assertEquals("body", el1.first().tagName());
        assertEquals("span", el1.last().tagName());

        Elements el2 = doc.select("p:not(:has(span))");
        assertEquals(1, el2.size());
        assertEquals("Two", el2.first().text());
    }

    @Test
    public void testPseudoHasAtRoot() {
        Document doc = Jsoup.parse("<div><p><span>One</span></p></div> <div><p>Two</p></div>");
        Elements hasSpan = doc.select(":has(span)");
        assertEquals(3, hasSpan.size()); // html, body, div, p -> depending on root, here html, body, div, p
    }
}