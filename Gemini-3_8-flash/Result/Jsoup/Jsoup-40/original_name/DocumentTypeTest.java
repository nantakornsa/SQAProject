package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentTypeTest {

    @Test
    public void constructorValidationOkWithBlankName() {
        DocumentType docType = new DocumentType("", "", "", "");
        assertEquals("", docType.attr("name"));
    }

    @Test
    public void handlesInvalidDoctypes() {
        Document doc = Jsoup.parse("<!DOCTYPE>One  <!DOCTYPE >Two <!DOCTYPE   >Three");
        assertEquals("<!DOCTYPE>One <!DOCTYPE>Two <!DOCTYPE>Three", doc.body().html());
    }
}