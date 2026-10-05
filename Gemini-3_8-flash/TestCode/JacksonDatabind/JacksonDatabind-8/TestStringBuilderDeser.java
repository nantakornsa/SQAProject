package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestStringBuilderDeser extends BaseMapTest
{
    public void testStringBuilder() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = mapper.readValue(quote("abc"), StringBuilder.class);
        assertEquals("abc", sb.toString());
    }
}