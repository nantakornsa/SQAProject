package org.jsoup.nodes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntitiesRegressionTest {

    @Test
    public void testNoSpuriousNamedEntityDecodesWithoutSemicolon() {
        // Extended entities without a terminating semicolon should not be decoded,
        // preventing spurious decodes in URLs and text.
        String text = "&angst &angst; http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";
        String expected = "&angst \u00c5 http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";

        assertEquals(expected, Entities.unescape(text));
    }
}