package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.XmlDeclaration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlDeclarationTest {

    @Test
    public void testParseDeclarationAttributes() {
        String xml = "<?xml version='1' encoding='UTF-8' something='else'?><val>One</val>";
        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
        XmlDeclaration decl = (XmlDeclaration) doc.childNode(0);

        assertEquals("xml", decl.name());
        assertEquals("1", decl.attr("version"));
        assertEquals("UTF-8", decl.attr("encoding"));
        assertEquals("else", decl.attr("something"));
        assertEquals("version=\"1\" encoding=\"UTF-8\" something=\"else\"", decl.getWholeDeclaration());
        assertEquals("<?xml version=\"1\" encoding=\"UTF-8\" something=\"else\"?>", decl.outerHtml());
    }
}