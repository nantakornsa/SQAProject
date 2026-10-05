package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HtmlParserTemplateTableTest {

    @Test
    public void testTemplateInsideTableBodyAndRow() {
        String html = "<table>" +
                "<tbody>" +
                "<template id=\"t1\"><tr><td>Row 1</td></tr></template>" +
                "<tr><template id=\"t2\"><td>Cell 2</td></template></tr>" +
                "</tbody>" +
                "</table>";

        Document doc = Jsoup.parse(html);

        Element t1 = doc.getElementById("t1");
        assertNotNull("Template t1 should exist inside tbody", t1);
        assertEquals("tbody", t1.parent().tagName());
        assertEquals(1, t1.children().size());
        assertEquals("tr", t1.child(0).tagName());

        Element t2 = doc.getElementById("t2");
        assertNotNull("Template t2 should exist inside tr", t2);
        assertEquals("tr", t2.parent().tagName());
        assertEquals(1, t2.children().size());
        assertEquals("td", t2.child(0).tagName());
    }
}