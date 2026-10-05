package com.fasterxml.jackson.databind.type;

import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JavaType;

public class ReferenceTypeCanonicalTest extends BaseMapTest
{
    public void testReferenceTypeCanonical()
    {
        TypeFactory tf = TypeFactory.defaultInstance();
        JavaType type = tf.constructType(new TypeReference<AtomicReference<Long>>() { });
        assertEquals("java.util.concurrent.atomic.AtomicReference<java.lang.Long>", type.toCanonical());
    }
}