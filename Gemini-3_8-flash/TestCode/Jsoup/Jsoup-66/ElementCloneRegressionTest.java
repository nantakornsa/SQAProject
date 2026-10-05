package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementCloneRegressionTest {

    @Test
    public void testNextElementSiblingAfterClone() {
        String html = "<!DOCTYPE html><html lang=\"en\"><head></head><body><div>Initial element</div></body></html>";
        String expectedText = "New element";
        String cloneExpect = "New element in clone";

        Document original = Jsoup.parse(html);
        Document clone = original.clone();

        Element originalElement = original.body().child(0);
        originalElement.after("<div>" + expectedText + "</div>");
        Element originalNextElementSibling = originalElement.nextElementSibling();
        Element originalNextSibling = (Element) originalElement.nextSibling();
        assertEquals(expectedText, originalNextElementSibling.text());
        assertEquals(expectedText, originalNextSibling.text());

        Element cloneElement = clone.body().child(0);
        cloneElement.after("<div>" + cloneExpect + "</div>");
        Element cloneNextElementSibling = cloneElement.nextElementSibling();
        Element cloneNextSibling = (Element) cloneElement.nextSibling();
        assertEquals(cloneExpect, cloneNextElementSibling.text());
        assertEquals(cloneExpect, cloneNextSibling.text());
    }
}