package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeminiHtmlParserTest {

    @Test
    public void testReinsertionModeForThCells() {
        String body = "<body> <table> <tr> <th> <table><tr><td></td></tr></table> <div> <table><tr><td></td></tr></table> </div> <div></div> <div></div> <div></div> </th> </tr> </table> </body>";
        Document doc = Jsoup.parse(body);
        assertEquals(1, doc.body().children().size());
    }
}