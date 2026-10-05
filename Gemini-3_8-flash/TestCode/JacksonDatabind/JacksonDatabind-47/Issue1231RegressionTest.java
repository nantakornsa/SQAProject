package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Issue1231RegressionTest extends BaseMapTest {

    static class BaseClass {
    }

    @JsonPropertyOrder({ "a" })
    static abstract class AbstractSubClass extends BaseClass {
        public int a = 3;
    }

    static class ConcreteSubClass extends AbstractSubClass {
        public int b = 4;
    }

    static class Container {
        @JsonSerialize(as = AbstractSubClass.class)
        public BaseClass value = new ConcreteSubClass();
    }

    public void testSpecializedSerializationType() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new Container());
        assertEquals("{\"value\":{\"a\":3}}", json);
    }
}