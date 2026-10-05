package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AttributeParseTest {

    @Test
    public void dropsSlashFromAttributeName() {
        String html = "<img /onerror='doMyJob'/>";
        Document doc = Jsoup.parse(html);
        assertTrue("SelfClosingStartTag ignores last character", doc.select("img[onerror]").size() != 0);
        assertEquals("<img onerror=\"doMyJob\">", doc.body().html());

        doc = Jsoup.parse(html, "", Parser.xmlParser());
        assertEquals("<img onerror=\"doMyJob\" />", doc.html());
    }
}