package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeBuilderTest {

    @Test
    public void testRecycleCurrentTokenInTable() {
        // When <tr> is encountered directly in <table>, HtmlTreeBuilder emits a synthetic <tbody>
        // by calling processStartTag("tbody") while currentToken is still the <tr> start token.
        // If the start token is recycled, <tr> is overwritten with <tbody>.
        String html = "<table><!-- Comment --><tr><td>Why am I here?</td></tr></table>";
        Document doc = Jsoup.parse(html);

        assertEquals(1, doc.select("tr").size());
        assertEquals("Why am I here?", doc.select("tr td").text());

        String rendered = doc.body().html();
        int commentPos = rendered.indexOf("<!-- Comment -->");
        int textPos = rendered.indexOf("Why am I here?");
        org.junit.Assert.assertTrue("Comment not found", commentPos > -1);
        org.junit.Assert.assertTrue("Search text not found", textPos > -1);
        org.junit.Assert.assertTrue("Search text did not come after comment", textPos > commentPos);
    }
}