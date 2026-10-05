package com.fasterxml.jackson.dataformat.xml.ser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlTestBase;

public class TestBinaryStreamToXMLSerialization extends XmlTestBase
{
    // Writes the stream through JsonGenerator.writeBinary(InputStream, int)
    public static class StreamSerializer extends JsonSerializer<InputStream>
    {
        @Override
        public void serialize(InputStream value, JsonGenerator gen, SerializerProvider provider)
            throws IOException
        {
            gen.writeBinary(value, value.available());
        }
    }

    public static class TestPojo
    {
        @JsonSerialize(using = StreamSerializer.class)
        public InputStream field;

        public TestPojo() { }

        public TestPojo(InputStream f) { field = f; }
    }

    private final XmlMapper XML_MAPPER = new XmlMapper();

    public void testWith0Bytes() throws Exception {
        _testWith();
    }

    public void testWith1Byte() throws Exception {
        _testWith((byte) 1);
    }

    public void testWith2Bytes() throws Exception {
        _testWith((byte) 1, (byte) 2);
    }

    public void testWith3Bytes() throws Exception {
        _testWith((byte) 1, (byte) 2, (byte) 3);
    }

    public void testWith4Bytes() throws Exception {
        _testWith((byte) 1, (byte) 2, (byte) 3, (byte) 4);
    }

    private void _testWith(byte... bytes) throws Exception
    {
        String xml = XML_MAPPER.writeValueAsString(
                new TestPojo(new ByteArrayInputStream(bytes)));
        assertNotNull(xml);
        assertTrue("Should contain field element: " + xml, xml.contains("field"));
        if (bytes.length > 0) {
            String expected = Base64.getEncoder().encodeToString(bytes);
            assertTrue("Expected base64 '" + expected + "' in: " + xml,
                    xml.contains(">" + expected + "</field>"));
        }
    }
}