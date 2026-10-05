package com.fasterxml.jackson.databind.deser.jdk;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilCollectionsTypesTest extends BaseMapTest
{
    private final ObjectMapper MAPPER = newObjectMapper();

    public void testUnmodifiableListFromLinkedList() throws Exception
    {
        List<String> original = new LinkedList<String>();
        original.add("first");
        original.add("second");
        List<String> unmodifiable = Collections.unmodifiableList(original);

        String json = MAPPER.writeValueAsString(unmodifiable);

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) MAPPER.readValue(json, unmodifiable.getClass());

        assertEquals(original, result);
    }
}