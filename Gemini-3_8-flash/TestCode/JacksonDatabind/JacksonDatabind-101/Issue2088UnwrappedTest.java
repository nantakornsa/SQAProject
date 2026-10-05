package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Issue2088UnwrappedTest extends BaseMapTest
{
    static class UnwrappedObj {
        public String last;
    }

    static class OuterObj {
        public String first;

        @JsonUnwrapped
        public UnwrappedObj unwrapped;

        @JsonCreator
        public OuterObj(@JsonProperty("first") String first) {
            this.first = first;
        }
    }

    public void testIssue2088UnwrappedFieldsAfterLastCreatorProp() throws Exception
    {
        ObjectMapper mapper = newJsonMapper();
        String json = aposToQuotes("{'first':'1','last':'2'}");
        OuterObj result = mapper.readValue(json, OuterObj.class);
        assertEquals("1", result.first);
        assertNotNull(result.unwrapped);
        assertEquals("2", result.unwrapped.last);
    }
}