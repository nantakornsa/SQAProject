package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringArrayDeserializationTest extends BaseMapTest
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testStringArrayWithNull() throws Exception
    {
        String json = "[ \"a\", null, \"b\" ]";
        String[] result = MAPPER.readValue(json, String[].class);

        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertNull(result[1]);
        assertEquals("b", result[2]);
    }
}