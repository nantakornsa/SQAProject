package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class DataUtilCharsetTest {

    @Test
    public void supportsXmlCharsetDeclaration() throws IOException {
        String encoding = "iso-8859-1";
        InputStream soup = new ByteArrayInputStream((
            "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">Hellö Wörld!</html>"
        ).getBytes(encoding));

        Document doc = Jsoup.parse(soup, null, "");
        assertEquals("Hellö Wörld!", doc.body().text());
    }
}