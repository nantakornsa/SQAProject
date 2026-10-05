package com.fasterxml.jackson.databind.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IgnorePropertyOnDeserTest extends BaseMapTest
{
    static class MyBean1595 {
        private String name;

        @JsonIgnore
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testIgnoreGetterNotSetter1595() throws Exception
    {
        MyBean1595 bean = MAPPER.readValue("{\"name\":\"jack\"}", MyBean1595.class);
        assertEquals("jack", bean.name);
    }
}