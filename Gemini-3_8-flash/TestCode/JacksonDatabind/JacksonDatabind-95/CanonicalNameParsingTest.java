package com.fasterxml.jackson.databind.type;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JavaType;

public class CanonicalNameParsingTest extends BaseMapTest
{
    /**
     * Test for [databind#1941]: regression in TypeParser / TypeFactory where passing
     * null bindings causes NullPointerException.
     */
    public void testCanonicalNameParsing()
    {
        TypeFactory tf = TypeFactory.defaultInstance();

        // Testing parsing from canonical string representation
        JavaType type1 = tf.constructFromCanonical(Object.class.getName());
        assertNotNull(type1);
        assertEquals(Object.class, type1.getRawClass());

        JavaType type2 = tf.constructFromCanonical(String.class.getName());
        assertNotNull(type2);
        assertEquals(String.class, type2.getRawClass());

        // Testing constructParametricType with Class<?> arguments
        JavaType listType = tf.constructParametricType(ArrayList.class, String.class);
        assertNotNull(listType);
        assertEquals(ArrayList.class, listType.getRawClass());
        assertEquals(1, listType.containedTypeCount());
        assertEquals(String.class, listType.containedType(0).getRawClass());
    }
}