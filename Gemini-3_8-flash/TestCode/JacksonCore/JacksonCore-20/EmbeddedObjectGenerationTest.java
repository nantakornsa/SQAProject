package com.fasterxml.jackson.core.base64;

import java.io.StringWriter;

import com.fasterxml.jackson.core.BaseTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class EmbeddedObjectGenerationTest extends BaseTest {
    public void testBinaryAsEmbeddedObject() throws Exception {
        JsonFactory f = new JsonFactory();
        StringWriter sw = new StringWriter();
        JsonGenerator g = f.createGenerator(sw);
        byte[] data = new byte[] { 1, 2, 3, 4 };
        g.writeStartArray();
        g.writeEmbeddedObject(data);
        g.writeEmbeddedObject(null);
        g.writeEndArray();
        g.close();

        assertEquals("[\"AQIDBA==\",null]", sw.toString().trim());
    }
}