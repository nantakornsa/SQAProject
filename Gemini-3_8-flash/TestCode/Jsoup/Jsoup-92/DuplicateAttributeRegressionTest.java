package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DuplicateAttributeRegressionTest {

    @Test
    public void dropsDuplicateAttributesHtmlDefault() {
        // According to HTML spec, the first attribute seen should be preserved, later duplicates dropped.
        // In the buggy version, Attributes.put replaced earlier values with later values and didn't deduplicate properly.
        String html = "<p One=One ONE=Two One=Three one=Four two=Five TWO=Six>Text</p>";
        Parser parser = Parser.htmlParser().setTrackErrors(10);
        Document doc = parser.parseInput(html, "");

        Element p = doc.selectFirst("p");
        assertEquals("<p one=\"One\" two=\"Five\">Text</p>", p.outerHtml());
        assertEquals(4, parser.getErrors().size());
        assertEquals("Duplicate attribute", parser.getErrors().get(0).getErrorMessage());
    }

    @Test
    public void retainsAttributesOfDifferentCaseIfSensitive() {
        // When case sensitivity is preserved, attributes differing only by case should be retained,
        // but duplicate attributes with the same case should retain the first value.
        String html = "<p One=One One=Two one=Three two=Four two=Five Two=Six>Text</p>";
        Parser parser = Parser.htmlParser().settings(ParseSettings.preserveCase);
        Document doc = parser.parseInput(html, "");
        assertEquals("<p One=\"One\" one=\"Three\" two=\"Four\" Two=\"Six\">Text</p>", doc.selectFirst("p").outerHtml());
    }
}