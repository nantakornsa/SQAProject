package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolymorphicEmptyStringAsNullTest extends BaseMapTest {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = AsPropertyImpl.class)
    interface AsProperty { }

    static class AsPropertyImpl implements AsProperty {
        public int a;
    }

    public void testEmptyStringAsNullObjectWithAsPropertyTypeInfo() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        AsProperty result = mapper.readValue("\"\"", AsProperty.class);
        assertNull(result);

        result = mapper.readValue("\"   \"", AsProperty.class);
        assertNull(result);
    }
}