package com.fasterxml.jackson.databind.deser;

import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJDKAtomicTypes1256Test extends BaseMapTest
{
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    static class EmptyAtomicRef {
        public AtomicReference<String> a = new AtomicReference<String>();
    }

    public void testEmpty1256() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        assertEquals("{}", mapper.writeValueAsString(new EmptyAtomicRef()));
    }
}