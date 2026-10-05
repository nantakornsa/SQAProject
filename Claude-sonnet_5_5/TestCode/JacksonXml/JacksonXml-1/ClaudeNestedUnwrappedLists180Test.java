package com.fasterxml.jackson.dataformat.xml.lists;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlTestBase;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ClaudeNestedUnwrappedLists180Test extends XmlTestBase
{
    static class Records {
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<Record> records = new ArrayList<Record>();
    }

    static class Record {
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<Field> fields = new ArrayList<Field>();
    }

    static class Field {
        @JacksonXmlProperty(isAttribute = true)
        public String name;

        protected Field() { }
        public Field(String n) { name = n; }
    }

    private final XmlMapper MAPPER = new XmlMapper();

    public void testNestedUnwrappedLists180() throws Exception
    {
        Records input = new Records();
        input.records.add(new Record());
        input.records.add(new Record());
        input.records.get(0).fields.add(new Field("a"));
        input.records.get(1).fields.add(new Field("b"));

        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        String xml = MAPPER.writeValueAsString(input);

        Records result = MAPPER.readValue(xml, Records.class);
        assertNotNull(result.records);
        assertEquals(2, result.records.size());
        assertNotNull(result.records.get(0));
        assertNotNull(result.records.get(1));
        assertNotNull(result.records.get(0).fields);
        assertEquals(1, result.records.get(0).fields.size());
        assertEquals("a", result.records.get(0).fields.get(0).name);
        assertNotNull(result.records.get(1).fields);
        assertEquals(1, result.records.get(1).fields.size());
        assertEquals("b", result.records.get(1).fields.get(0).name);
    }
}