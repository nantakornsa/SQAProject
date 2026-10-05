package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentTypeRoundTripTest {

    @Test
    public void testRoundTripWithSystemKeyword() {
        String html = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";

        Document docHtml = Jsoup.parse(html);
        assertEquals(html, docHtml.childNode(0).outerHtml());

        Document docXml = Jsoup.parse(html, "", Parser.xmlParser());
        assertEquals(html, docXml.childNode(0).outerHtml());
    }

    @Test
    public void testRoundTripWithLegacyCompat() {
        String html = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";

        Document docHtml = Jsoup.parse(html);
        assertEquals(html, docHtml.childNode(0).outerHtml());

        Document docXml = Jsoup.parse(html, "", Parser.xmlParser());
        assertEquals(html, docXml.childNode(0).outerHtml());
    }
}