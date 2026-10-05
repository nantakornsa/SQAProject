package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringArrayDeserializerTest extends BaseMapTest
{
    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testArrayIndexForExceptions() throws Exception
    {
        try {
            MAPPER.readValue("[\"a\", {}]", String[].class);
            fail("Expected JsonMappingException");
        } catch (JsonMappingException e) {
            assertEquals(1, e.getPath().size());
            assertEquals(1, e.getPath().get(0).getIndex());
        }
    }
}