package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PreservedCaseNestingTest {

    @Test
    public void preservedCaseLinksCantNest() {
        String html = "<A>ONE <A>Two</A></A>";
        Document doc = Parser.htmlParser()
            .settings(ParseSettings.preserveCase)
            .parseInput(html, "");
        assertEquals("<A> ONE </A> <A> Two </A>", StringUtil.normaliseWhitespace(doc.body().html()));
    }
}