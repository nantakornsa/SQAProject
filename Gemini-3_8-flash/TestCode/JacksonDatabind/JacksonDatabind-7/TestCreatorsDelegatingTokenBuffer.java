package com.fasterxml.jackson.databind.creators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TokenBuffer;

public class TestCreatorsDelegatingTokenBuffer extends BaseMapTest {

    static class DelegateWithTokenBuffer {
        final TokenBuffer buffer;

        @JsonCreator
        public DelegateWithTokenBuffer(TokenBuffer b) {
            buffer = b;
        }
    }

    public void testDelegateWithTokenBuffer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DelegateWithTokenBuffer obj = mapper.readValue("{\"a\":1}", DelegateWithTokenBuffer.class);
        assertNotNull(obj);
        assertNotNull(obj.buffer);

        JsonParser jp = obj.buffer.asParser();
        assertToken(JsonToken.START_OBJECT, jp.nextToken());
        assertToken(JsonToken.FIELD_NAME, jp.nextToken());
        assertEquals("a", jp.getCurrentName());
        assertToken(JsonToken.VALUE_NUMBER_INT, jp.nextToken());
        assertEquals(1, jp.getIntValue());
        assertToken(JsonToken.END_OBJECT, jp.nextToken());
        assertNull(jp.nextToken());
        jp.close();
    }
}