package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserScriptTest {

    @Test
    public void handlesScriptInBody() {
        String html = "<p>Hello</p><script>obj.insert('<a rel=\"none\" />');\ni++;</script><p>There</p>";
        Document doc = Jsoup.parse(html);
        assertEquals("Hello There", doc.text());
        assertEquals("obj.insert('<a rel=\"none\" />');\ni++;", doc.data());
    }
}