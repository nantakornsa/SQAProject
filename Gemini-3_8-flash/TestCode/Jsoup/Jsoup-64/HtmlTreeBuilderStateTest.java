package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Regression test for Jsoup-64.
 * Tests handling of self-closing rawtext elements like {@code <style />} or {@code <noframes />}.
 */
public class HtmlTreeBuilderStateTest {

    @Test
    public void handlesKnownEmptyStyle() {
        String h = "<html><head><style /><meta name=foo></head><body>One</body></html>";
        Document doc = Jsoup.parse(h);
        // Stripping newlines for consistent comparison
        String html = doc.html().replaceAll("\r?\n", "");
        assertEquals("<html><head><style></style><meta name=\"foo\"></head><body>One</body></html>", html);
    }

    @Test
    public void handlesKnownEmptyNoFrames() {
        String h = "<html><head><noframes /><meta name=foo></head><body>One</body></html>";
        Document doc = Jsoup.parse(h);
        String html = doc.html().replaceAll("\r?\n", "");
        assertEquals("<html><head><noframes></noframes><meta name=\"foo\"></head><body>One</body></html>", html);
    }
}