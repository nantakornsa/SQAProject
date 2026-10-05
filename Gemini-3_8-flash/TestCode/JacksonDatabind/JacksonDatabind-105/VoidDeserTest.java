package com.fasterxml.jackson.databind.deser.jdk;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VoidDeserTest extends BaseMapTest {
    private final ObjectMapper MAPPER = newJsonMapper();

    public void testVoidDeser() throws Exception {
        Void result = MAPPER.readValue("null", Void.class);
        assertNull(result);

        result = MAPPER.readValue("\"foo\"", Void.class);
        assertNull(result);

        result = MAPPER.readValue("123", Void.class);
        assertNull(result);

        result = MAPPER.readValue("{}", Void.class);
        assertNull(result);
    }
}