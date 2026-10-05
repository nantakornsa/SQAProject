package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeDefaultTyping793Test extends BaseMapTest
{
    public void testArrayWithDefaultTyping() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

        String json = "{\"items\":[1, 2]}";
        JsonNode node = mapper.readTree(json);
        assertNotNull(node);
        assertTrue(node.has("items"));

        // Also verify round-trip serialization / deserialization as JsonNode
        String serialized = mapper.writeValueAsString(node);
        JsonNode node2 = mapper.readValue(serialized, JsonNode.class);
        assertEquals(node, node2);
    }
}