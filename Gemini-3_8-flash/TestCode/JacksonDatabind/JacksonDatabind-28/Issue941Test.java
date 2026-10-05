package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class Issue941Test extends BaseMapTest {
    public void testEmptyObjectNodeReadValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader(ObjectNode.class);
        MappingIterator<ObjectNode> it = reader.readValues("{}");
        assertTrue(it.hasNext());
        ObjectNode node = it.nextValue();
        assertNotNull(node);
        assertEquals(0, node.size());
    }
}