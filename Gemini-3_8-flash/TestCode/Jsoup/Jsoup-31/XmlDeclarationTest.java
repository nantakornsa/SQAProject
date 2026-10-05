package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlDeclarationTest {

    @Test
    public void handlesXmlDeclarationAsDeclaration() {
        String html = "<?xml encoding='UTF-8' ?><body>One</body><!-- comment -->";
        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
        assertEquals("<?xml encoding='UTF-8' ?> <body> One </body> <!-- comment -->",
                StringUtil.normaliseWhitespace(doc.outerHtml()));
        assertEquals("#declaration", doc.childNode(0).nodeName());
        assertEquals("#comment", doc.childNode(2).nodeName());
    }
}