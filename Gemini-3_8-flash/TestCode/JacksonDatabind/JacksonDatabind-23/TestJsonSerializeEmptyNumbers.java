package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonSerializeEmptyNumbers extends BaseMapTest
{
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    static class EmptyInt {
        public int value;

        public EmptyInt(int v) {
            value = v;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    static class EmptyLong {
        public long value;

        public EmptyLong(long v) {
            value = v;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    static class EmptyDouble {
        public double value;

        public EmptyDouble(double v) {
            value = v;
        }
    }

    public void testEmptyInclusionScalars() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        assertEquals("{}", mapper.writeValueAsString(new EmptyInt(0)));
        assertEquals("{\"value\":1}", mapper.writeValueAsString(new EmptyInt(1)));

        assertEquals("{}", mapper.writeValueAsString(new EmptyLong(0L)));
        assertEquals("{\"value\":1}", mapper.writeValueAsString(new EmptyLong(1L)));

        assertEquals("{}", mapper.writeValueAsString(new EmptyDouble(0.0)));
        assertEquals("{\"value\":1.0}", mapper.writeValueAsString(new EmptyDouble(1.0)));
    }
}