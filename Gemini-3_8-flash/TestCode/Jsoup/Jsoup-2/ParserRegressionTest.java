package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserRegressionTest {

    @Test
    public void handlesTextAfterData() {
        String h = "<html><body>pre <script>inner</script> aft</body></html>";
        Document doc = Jsoup.parse(h);
        assertEquals("<html><head></head><body>pre <script>inner</script> aft</body></html>", TextUtil.stripNewlines(doc.html()));
    }
}