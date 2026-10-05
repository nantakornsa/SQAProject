package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class W3CDomNamespaceTest {

    @Test
    public void treatsUndeclaredNamespaceAsLocalName() {
        String html = "<fb:like>One</fb:like>";
        org.jsoup.nodes.Document doc = Jsoup.parse(html);

        Document w3Doc = new W3CDom().fromJsoup(doc);
        Node htmlEl = w3Doc.getFirstChild();

        assertNull(htmlEl.getNamespaceURI());
        assertEquals("html", htmlEl.getLocalName());
        assertEquals("html", htmlEl.getNodeName());

        Node fb = htmlEl.getFirstChild().getNextSibling().getFirstChild();
        assertNull(fb.getNamespaceURI());
        assertEquals("like", fb.getLocalName());
        assertEquals("fb:like", fb.getNodeName());
    }
}