package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Issue1013UnwrappedTest extends BaseMapTest {

    static class Outer {
        @JsonUnwrapped
        protected Inner inner = new Inner();
    }

    static class Inner {
        public String value = "test";
    }

    public void testUnwrappedAsPropertyIndicator() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new Outer());
        assertEquals("{\"value\":\"test\"}", json);
    }
}