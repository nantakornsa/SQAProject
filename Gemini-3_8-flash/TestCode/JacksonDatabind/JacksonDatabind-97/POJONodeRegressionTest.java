package com.fasterxml.jackson.databind.node;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class POJONodeRegressionTest extends BaseMapTest {

    static class CustomData {
        public final String myStr;

        public CustomData(String str) {
            this.myStr = str;
        }
    }

    static class CustomDataSerializer extends JsonSerializer<CustomData> {
        @Override
        public void serialize(CustomData value, JsonGenerator gen, SerializerProvider ctxt) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("myStr", "The value is: " + ctxt.getAttribute("myAttr"));
            gen.writeEndObject();
        }
    }

    public void testPOJONodeCustomSer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(CustomData.class, new CustomDataSerializer());
        mapper.registerModule(module);

        ObjectNode node = mapper.createObjectNode();
        node.putPOJO("data", new CustomData("ignored"));

        String json = mapper.writer()
                .withAttribute("myAttr", "Hello!")
                .writeValueAsString(node);

        assertEquals("{\"data\":{\"myStr\":\"The value is: Hello!\"}}", json);
    }
}