package com.fasterxml.jackson.databind.deser;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CharSequenceKeyDeserializerTest extends BaseMapTest
{
    private final ObjectMapper MAPPER = newObjectMapper();

    public void testCharSequenceKeyMap() throws Exception
    {
        String json = aposToQuotes("{'a':'b'}");
        Map<CharSequence, String> result = MAPPER.readValue(json,
                new TypeReference<Map<CharSequence, String>>() { });
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("b", result.get("a"));
    }
}