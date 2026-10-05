package com.fasterxml.jackson.databind.deser.jdk;

import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AtomicReferenceNullTest extends BaseMapTest
{
    static class NestedAtomic {
        public AtomicReference<AtomicReference<String>> value;
    }

    private final ObjectMapper MAPPER = newObjectMapper();

    public void testNullWithinNested() throws Exception
    {
        NestedAtomic result = MAPPER.readValue("{\"value\":null}", NestedAtomic.class);
        assertNotNull(result);
        assertNotNull(result.value);
        assertNotNull(result.value.get());
        assertNull(result.value.get().get());

        AtomicReference<AtomicReference<String>> ref = MAPPER.readValue(
                "null",
                new TypeReference<AtomicReference<AtomicReference<String>>>() { });
        assertNotNull(ref);
        assertNotNull(ref.get());
        assertNull(ref.get().get());
    }
}