package com.fasterxml.jackson.databind.struct;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SingleValueAsArray1421Test extends BaseMapTest
{
    static class Bean1421 {
        protected List<String> values;

        @JsonCreator
        public Bean1421(List<String> v) {
            values = v;
        }

        public List<String> getValues() {
            return values;
        }
    }

    // Test for [databind#1421]: Bean with array delegate creator should accept single value
    // when ACCEPT_SINGLE_VALUE_AS_ARRAY is enabled.
    public void testWithSingleString() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        Bean1421 bean = mapper.readValue(quote("test2"), Bean1421.class);
        assertNotNull(bean);
        assertNotNull(bean.getValues());
        assertEquals(1, bean.getValues().size());
        assertEquals("test2", bean.getValues().get(0));
    }
}