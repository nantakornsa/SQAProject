package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;

public class AsyncLocationOffsetTest extends AsyncTestBase
{
    private final JsonFactory JSON_F = new JsonFactory();

    public void testLocationOffsets() throws Exception
    {
        NonBlockingJsonParser p = (NonBlockingJsonParser) JSON_F.createNonBlockingByteArrayParser();
        ByteArrayFeeder feeder = p.getNonBlockingInputFeeder();

        byte[] data = utf8Bytes("   [ true, false ]");

        // Feed first 2 bytes (spaces)
        feeder.feedInput(data, 0, 2);
        assertNull(p.nextToken());

        // Feed next 3 bytes with non-zero start offset: " [ "
        feeder.feedInput(data, 2, 5);
        assertToken(JsonToken.START_ARRAY, p.nextToken());
        JsonLocation loc = p.getTokenLocation();
        assertEquals(3, loc.getByteOffset());
        assertEquals(3, loc.getCharOffset());

        // Feed next 4 bytes: "true"
        feeder.feedInput(data, 5, 9);
        assertToken(JsonToken.VALUE_TRUE, p.nextToken());
        loc = p.getTokenLocation();
        assertEquals(5, loc.getByteOffset());
        assertEquals(5, loc.getCharOffset());

        p.close();
    }
}