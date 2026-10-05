package com.fasterxml.jackson.databind.interop;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test case for [databind#1599] verifying that blacklisted types cannot be deserialized
 * via polymorphic type handling.
 */
public class IllegalTypesCheckTest extends BaseMapTest
{
    static class Bean1599 {
        public Object val;
    }

    public void testIssue1599() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        final String JSON = aposToQuotes(
                "{'val': ['com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl', {}]}"
        );
        try {
            mapper.readValue(JSON, Bean1599.class);
            fail("Should not pass");
        } catch (JsonMappingException e) {
            verifyException(e, "Illegal type");
        }
    }
}