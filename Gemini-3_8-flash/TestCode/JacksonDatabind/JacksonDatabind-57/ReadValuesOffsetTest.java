package com.fasterxml.jackson.databind.seq;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class ReadValuesOffsetTest extends BaseMapTest {
    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testReadValuesWithOffsetAndLength() throws IOException {
        String json = "{\"a\":1} {\"a\":2}";
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);

        // Prefix and suffix padding around the valid JSON payload
        byte[] buffer = new byte[jsonBytes.length + 10];
        // Fill prefix with arbitrary non-JSON bytes that would trigger parse error if parsed from 0
        buffer[0] = '!';
        buffer[1] = '@';
        buffer[2] = '#';
        int offset = 3;
        System.arraycopy(jsonBytes, 0, buffer, offset, jsonBytes.length);

        ObjectReader reader = MAPPER.readerFor(POJO.class);
        MappingIterator<POJO> it = reader.readValues(buffer, offset, jsonBytes.length);

        assertTrue(it.hasNext());
        POJO p1 = it.nextValue();
        assertEquals(1, p1.a);

        assertTrue(it.hasNext());
        POJO p2 = it.nextValue();
        assertEquals(2, p2.a);

        assertFalse(it.hasNext());
        it.close();
    }

    static class POJO {
        public int a;
    }
}