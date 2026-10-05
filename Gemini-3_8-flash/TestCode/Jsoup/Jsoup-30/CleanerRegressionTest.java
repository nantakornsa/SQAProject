package org.jsoup.safety;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CleanerRegressionTest {

    @Test
    public void testIsValidWithComments() {
        String ok = "<p>Test <b><a href='http://example.com/'>OK</a></b></p>";
        String nokComment = "<!-- comment --><p>Not OK</p>";

        assertTrue(Jsoup.isValid(ok, Whitelist.basic()));
        assertFalse(Jsoup.isValid(nokComment, Whitelist.basic()));
    }
}