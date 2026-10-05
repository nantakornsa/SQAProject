package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrimitiveEmptyStringCoercionTest extends BaseMapTest {

    public void testEmptyToNullCoercionForPrimitives() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

        _testEmptyToNullFail(mapper, int.class);
        _testEmptyToNullFail(mapper, long.class);
        _testEmptyToNullFail(mapper, double.class);
        _testEmptyToNullFail(mapper, float.class);
        _testEmptyToNullFail(mapper, byte.class);
        _testEmptyToNullFail(mapper, short.class);
        _testEmptyToNullFail(mapper, boolean.class);
        _testEmptyToNullFail(mapper, char.class);
    }

    private void _testEmptyToNullFail(ObjectMapper mapper, Class<?> cls) throws Exception {
        try {
            mapper.readValue(quote(""), cls);
            fail("Expected JsonProcessingException when mapping empty String to " + cls.getName()
                    + " with FAIL_ON_NULL_FOR_PRIMITIVES enabled");
        } catch (JsonProcessingException e) {
            verifyException(e, "Can not map Empty String as null");
        }
    }
}