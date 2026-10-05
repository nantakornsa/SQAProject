package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserBugTest {

    @Test
    public void testParsesRoughAttributesWithoutIndexOutOfBoundsException() {
        String html = "<p =a>One<a =a";
        Document doc = Jsoup.parse(html);
        assertEquals("<p>One<a></a></p>", doc.body().html());

        doc = Jsoup.parse("<p .....");
        assertEquals("<p></p>", doc.body().html());

        doc = Jsoup.parse("<p .....<p!!");
        assertEquals("<p></p>\n<p></p>", doc.body().html());
    }
}