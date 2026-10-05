package com.fasterxml.jackson.databind.exc;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationDuplicationTest extends BaseMapTest
{
    protected enum ABC { A, B, C; }

    private final ObjectMapper MAPPER = newJsonMapper();

    public void testNoDuplicateLocationInMessage() throws Exception
    {
        try {
            MAPPER.readValue("{\"value\" : 3}", new TypeReference<Map<ABC, Integer>>() {});
            fail("Should not pass");
        } catch (JsonMappingException e) {
            String msg = e.getMessage();
            int ix = msg.indexOf(" at [");
            if (ix < 0) {
                fail("Should have 'at [' marker: " + msg);
            }
            int ix2 = msg.indexOf(" at [", ix + 1);
            if (ix2 > 0) {
                fail("Should only get one 'at [' marker, got 2, source: " + msg);
            }
        }
    }
}