package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class W3CDomTest {

    @Test
    public void handlesInvalidAttributeNames() {
        String html = "<html><head></head><body style=\"color: red\" \" name\"></body></html>";
        org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(html);
        Element body = jsoupDoc.select("body").first();
        assertTrue(body.hasAttr("\""));
        assertTrue(body.hasAttr("name\""));

        Document w3Doc = new W3CDom().fromJsoup(jsoupDoc);
        assertNotNull(w3Doc);
    }
}