package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ElementTextInvisibleCharTest {

    @Test
    public void testNormalizesInvisiblesInText() {
        // Invisible characters: soft hyphen (&shy; / \u00AD), zero width space (&#x200b; / \u200B),
        // zero width non-joiner (&#x200c; / \u200C), zero width joiner (&#x200d; / \u200D)
        String escaped = "This&shy;is&#x200b;one&#x200c;long&#x200d;word";
        String decoded = "This\u00ADis\u200Bone\u200Clong\u200Dword";

        Document doc = Jsoup.parse("<p>" + escaped + "</p>");
        Element p = doc.select("p").first();
        doc.outputSettings().charset("ascii");

        // text() should have invisible characters normalized away
        assertEquals("Thisisonelongword", p.text());

        // outerHtml and getWholeText should preserve them
        assertEquals("<p>" + escaped + "</p>", p.outerHtml());
        assertEquals(decoded, p.textNodes().get(0).getWholeText());

        // selector :contains should match against the normalized text without invisibles
        Element matched = doc.select("p:contains(Thisisonelongword)").first();
        assertNotNull(matched);
        assertEquals("p", matched.nodeName());
        assertTrue(matched.is(":containsOwn(Thisisonelongword)"));
    }
}