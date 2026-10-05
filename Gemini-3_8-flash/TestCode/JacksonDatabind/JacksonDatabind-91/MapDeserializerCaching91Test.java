package com.fasterxml.jackson.databind.deser.jdk;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class MapDeserializerCaching91Test extends BaseMapTest {

    static class CustomKeyDeserializer extends KeyDeserializer {
        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) {
            return key + " (CUSTOM)";
        }
    }

    static class NonAnnotatedMapHolder {
        public Map<String, String> data;
    }

    static class AnnotatedMapHolder {
        @JsonDeserialize(keyUsing = CustomKeyDeserializer.class)
        public Map<String, String> data;
    }

    public void testCustomKeyDeserializerCacheWithAnnotatedMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = aposToQuotes("{'data':{'1st':'onedata','2nd':'twodata'}}");

        // Deserialize with non-annotated map first to populate the deserializer cache
        NonAnnotatedMapHolder nonAnnotated = mapper.readValue(json, NonAnnotatedMapHolder.class);
        assertNotNull(nonAnnotated.data);
        assertTrue(nonAnnotated.data.containsKey("1st"));
        assertTrue(nonAnnotated.data.containsKey("2nd"));

        // Deserialize with annotated map - must use the custom key deserializer instead of cached standard deserializer
        AnnotatedMapHolder annotated = mapper.readValue(json, AnnotatedMapHolder.class);
        assertNotNull(annotated.data);
        if (!annotated.data.containsKey("1st (CUSTOM)") || !annotated.data.containsKey("2nd (CUSTOM)")) {
            fail("Not using custom key deserializer for input: " + json + "; resulted in: " + annotated.data);
        }
    }
}