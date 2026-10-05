package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementAppendTableTest {

    @Test
    public void testAppendRowToTable() {
        Document doc = Jsoup.parse("<table><tr><td>1</td></tr></table>");
        Element table = doc.select("table").first();
        table.append("<tr><td>2</td></tr>");

        String cleanHtml = doc.body().html().replaceAll("\\r?\\n", "");
        assertEquals("<table><tr><td>1</td></tr><tr><td>2</td></tr></table>", cleanHtml);
    }

    @Test
    public void testPrependRowToTable() {
        Document doc = Jsoup.parse("<table><tr><td>1</td></tr></table>");
        Element table = doc.select("table").first();
        table.prepend("<tr><td>2</td></tr>");

        String cleanHtml = doc.body().html().replaceAll("\\r?\\n", "");
        assertEquals("<table><tr><td>2</td></tr><tr><td>1</td></tr></table>", cleanHtml);
    }
}