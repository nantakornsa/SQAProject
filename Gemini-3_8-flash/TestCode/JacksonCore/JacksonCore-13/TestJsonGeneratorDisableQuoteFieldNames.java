package com.fasterxml.jackson.core.json;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import com.fasterxml.jackson.core.BaseTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class TestJsonGeneratorDisableQuoteFieldNames extends BaseTest {

    public void testDisableQuoteFieldNamesWriter() throws Exception {
        JsonFactory f = new JsonFactory();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = f.createGenerator(sw);
        gen.disable(JsonGenerator.Feature.QUOTE_FIELD_NAMES);

        gen.writeStartObject();
        gen.writeNumberField("foo", 1);
        gen.writeEndObject();
        gen.close();

        assertEquals("{foo:1}", sw.toString());
    }

    public void testDisableQuoteFieldNamesStream() throws Exception {
        JsonFactory f = new JsonFactory();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        JsonGenerator gen = f.createGenerator(bytes);
        gen.disable(JsonGenerator.Feature.QUOTE_FIELD_NAMES);

        gen.writeStartObject();
        gen.writeNumberField("foo", 1);
        gen.writeEndObject();
        gen.close();

        assertEquals("{foo:1}", bytes.toString("UTF-8"));
    }
}