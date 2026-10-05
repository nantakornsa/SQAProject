package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GeminiParserSequenceTest {

    @Test
    public void testInitialized() throws Exception {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser p1 = jsonFactory.createParser("[ 1 ]");
        JsonParser p2 = jsonFactory.createParser("[ 2 ]");

        // Advance p2 before creating sequence
        assertEquals(JsonToken.START_ARRAY, p2.nextToken());

        JsonParserSequence seq = JsonParserSequence.createFlattened(false, p1, p2);

        assertEquals(JsonToken.START_ARRAY, seq.nextToken());
        assertEquals(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(1, seq.getIntValue());
        assertEquals(JsonToken.END_ARRAY, seq.nextToken());

        // Now switches to p2, which is already at START_ARRAY
        assertEquals(JsonToken.START_ARRAY, seq.nextToken());
        assertEquals(JsonToken.VALUE_NUMBER_INT, seq.nextToken());
        assertEquals(2, seq.getIntValue());
        assertEquals(JsonToken.END_ARRAY, seq.nextToken());
        assertNull(seq.nextToken());

        seq.close();
    }
}