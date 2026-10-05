package com.fasterxml.jackson.databind.ser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Issue705AnyGetterCustomSerializerTest extends BaseMapTest
{
    static class Issue705Bean {
        protected Map<String, String> stuff;

        public Issue705Bean(String key, String value) {
            stuff = new HashMap<String, String>();
            stuff.put(key, value);
        }

        @JsonSerialize(using = Issue705Serializer.class)
        @JsonAnyGetter
        public Map<String, String> getStuff() {
            return stuff;
        }
    }

    static class Issue705Serializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException
        {
            Map<?, ?> map = (Map<?, ?>) value;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                jgen.writeFieldName("stuff");
                jgen.writeString(entry.getKey() + "/" + entry.getValue());
            }
        }
    }

    public void testCustomAnyGetterSerializer() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(new Issue705Bean("key", "value"));
        assertEquals("{\"stuff\":\"key/value\"}", json);
    }
}