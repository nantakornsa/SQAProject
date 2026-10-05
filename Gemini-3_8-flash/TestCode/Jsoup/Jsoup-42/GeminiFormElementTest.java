package org.jsoup.nodes;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeminiFormElementTest {

    @Test
    public void testFormDataWithDisabledInputsAndDefaultCheckboxValue() {
        String html = "<form>" +
                "<input name='check' type='checkbox' checked>" +
                "<input name='disabledInput' value='shouldNotAppear' disabled>" +
                "</form>";
        Document doc = Jsoup.parse(html);
        FormElement form = (FormElement) doc.select("form").first();
        List<Connection.KeyVal> data = form.formData();

        assertEquals(1, data.size());
        assertEquals("check", data.get(0).key());
        assertEquals("on", data.get(0).value());
    }
}