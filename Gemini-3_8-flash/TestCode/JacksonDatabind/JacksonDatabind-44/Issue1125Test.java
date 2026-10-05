package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Issue1125Test extends BaseMapTest {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Default1125.class)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Sub1125.class, name = "sub")
    })
    static abstract class Base1125 {
        public int a;
    }

    static class Default1125 extends Base1125 {
        public int def;
    }

    static class Sub1125 extends Base1125 {
        public int b;
    }

    public void testIssue1125WithDefault() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Base1125 value = mapper.readValue("{\"type\":\"sub\",\"a\":1,\"b\":2}", Base1125.class);
        assertNotNull(value);
        assertEquals(Sub1125.class, value.getClass());
        Sub1125 sub = (Sub1125) value;
        assertEquals(1, sub.a);
        assertEquals(2, sub.b);
    }
}