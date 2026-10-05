package com.fasterxml.jackson.databind.jsontype;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiDefaultTypingWithPrimitivesTest extends BaseMapTest
{
    static class Data {
        public long key;

        public Data() { }

        public Data(long key) {
            this.key = key;
        }
    }

    public void testDefaultTypingWithLong() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("longAsField", new Data(123L));

        String json = mapper.writeValueAsString(map);

        Map<?, ?> result = mapper.readValue(json, Map.class);
        assertNotNull(result);
        Object dataObj = result.get("longAsField");
        assertTrue(dataObj instanceof Data);
        assertEquals(123L, ((Data) dataObj).key);
    }
}