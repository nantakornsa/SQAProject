package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JavaType;

public class DeprecatedTypeResolutionTest extends BaseMapTest
{
    @SuppressWarnings("deprecation")
    public void testDeprecatedTypeResolution()
    {
        TypeFactory tf = TypeFactory.defaultInstance();

        JavaType t1 = tf.constructType(String.class, (Class<?>) null);
        assertNotNull(t1);
        assertEquals(String.class, t1.getRawClass());

        JavaType t2 = tf.constructType(String.class, (JavaType) null);
        assertNotNull(t2);
        assertEquals(String.class, t2.getRawClass());
    }
}