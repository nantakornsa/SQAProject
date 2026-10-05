package com.fasterxml.jackson.databind.creators;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiArrayDelegatorCreatorForCollectionTest extends BaseMapTest
{
    public void testUnmodifiable() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Set<?> unmodSet = Collections.unmodifiableSet(Collections.singleton("foo"));
        TypeReference<?> typeRef = new TypeReference<Set<Object>>() { };
        JavaType targetType = mapper.getTypeFactory().constructType(unmodSet.getClass());

        Object result = mapper.readValue("[\"abc\"]", targetType);
        assertNotNull(result);
        assertTrue(result instanceof Set);
        Set<?> set = (Set<?>) result;
        assertEquals(1, set.size());
        assertTrue(set.contains("abc"));
    }
}