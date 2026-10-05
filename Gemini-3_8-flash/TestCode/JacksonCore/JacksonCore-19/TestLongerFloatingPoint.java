package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class TestLongerFloatingPoint {

    @Test
    public void testLongerFloatingPointReader() throws Exception {
        JsonFactory f = new JsonFactory();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; ++i) {
            sb.append('1');
        }
        sb.append(".0");
        String doc = sb.toString();

        JsonParser p = f.createParser(new StringReader(doc));
        Assert.assertEquals(JsonToken.VALUE_NUMBER_FLOAT, p.nextToken());
        Assert.assertEquals(doc, p.getText());
        p.close();
    }

    @Test
    public void testLongerFloatingPointStream() throws Exception {
        JsonFactory f = new JsonFactory();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; ++i) {
            sb.append('1');
        }
        sb.append(".0");
        String doc = sb.toString();

        JsonParser p = f.createParser(new ByteArrayInputStream(doc.getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals(JsonToken.VALUE_NUMBER_FLOAT, p.nextToken());
        Assert.assertEquals(doc, p.getText());
        p.close();
    }
}