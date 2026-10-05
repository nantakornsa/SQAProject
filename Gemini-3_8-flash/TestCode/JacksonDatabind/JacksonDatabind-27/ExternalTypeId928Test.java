package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalTypeId928Test extends BaseMapTest {

    static class Server928 {
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = BasePayload928.class, name = "base"),
        })
        public Payload928 payload;

        @JsonCreator
        public Server928(@JsonProperty("payload") Payload928 payload) {
            this.payload = payload;
        }
    }

    static class Payload928 { }

    static class BasePayload928 extends Payload928 {
        public int a;
    }

    public void testInverseExternalId928() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Server928 server = mapper.readValue("{\"payload\":{\"a\":1},\"type\":\"base\"}", Server928.class);
        assertNotNull(server);
        assertNotNull(server.payload);
        assertEquals(BasePayload928.class, server.payload.getClass());
        assertEquals(1, ((BasePayload928) server.payload).a);
    }
}