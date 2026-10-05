package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.BaseMapTest;
import java.io.IOException;

public class TokenBufferOutputContextTest extends BaseMapTest
{
    public void testOutputContextCurrentName() throws IOException
    {
        TokenBuffer buf = new TokenBuffer(null, false);
        buf.writeStartObject();
        buf.writeFieldName("a");
        buf.writeString("1");
        buf.writeFieldName("b");

        assertEquals("b", buf.getOutputContext().getCurrentName());

        buf.writeNumber(2);
        buf.writeEndObject();
        buf.close();
    }
}