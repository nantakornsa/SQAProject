package com.fasterxml.jackson.databind.type;

import java.util.concurrent.atomic.AtomicReference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JavaType;

public class ReferenceTypeGenericSignatureTest extends BaseMapTest
{
    public void testGenericSignature1194() throws Exception
    {
        JavaType type = TypeFactory.defaultInstance().constructType(
                new TypeReference<AtomicReference<String>>() {});
        assertEquals("Ljava/util/concurrent/atomic/AtomicReference<Ljava/lang/String;>;",
                type.getGenericSignature());
    }
}