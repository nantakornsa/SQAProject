package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parsesUnterminatedTextarea() {
        // don't parse right to end, but break on <p>
        Document doc = Jsoup.parse("<body><p><textarea>one<p>two");
        Element t = doc.select("textarea").first();
        assertEquals("one", t.text());
        assertEquals("two", doc.select("p").get(1).text());
    }

    @Test
    public void handlesUnclosedTitle() {
        Document one = Jsoup.parse("<title>One <b>Two <p>Test</p>");
        assertEquals("One", one.title());
        assertEquals("Two", one.select("b").first().text());
    }
}