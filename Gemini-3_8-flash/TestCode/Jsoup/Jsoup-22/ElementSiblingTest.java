package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ElementSiblingTest {

    @Test
    public void testElementIsNotASiblingOfItself() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three</div>");
        Element p2 = doc.select("p").get(1);

        assertEquals("Two", p2.text());
        Elements els = p2.siblingElements();
        assertEquals(2, els.size());
        assertEquals("<p>One</p>", els.get(0).outerHtml());
        assertEquals("<p>Three</p>", els.get(1).outerHtml());
    }

    @Test
    public void testOrphanNodeReturnsEmptySiblingElements() {
        Element el = new Element(org.jsoup.parser.Tag.valueOf("p"), "");
        assertEquals(0, el.siblingElements().size());
        assertNull(el.previousElementSibling());
        assertNull(el.nextElementSibling());
    }

    @Test
    public void testNodeIsNotASiblingOfItself() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three</div>");
        Element p2 = doc.select("p").get(1);

        List<Node> nodes = p2.siblingNodes();
        assertEquals(2, nodes.size());
        assertEquals("<p>One</p>", nodes.get(0).outerHtml());
        assertEquals("<p>Three</p>", nodes.get(1).outerHtml());
    }
}