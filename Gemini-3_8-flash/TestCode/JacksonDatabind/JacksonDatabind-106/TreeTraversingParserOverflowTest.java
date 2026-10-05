package com.fasterxml.jackson.databind.node;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BaseMapTest;

public class TreeTraversingParserOverflowTest extends BaseMapTest
{
    public void testNumberOverflowInt() throws IOException
    {
        BigInteger big = BigInteger.valueOf(Long.MAX_VALUE);
        JsonParser p = new TreeTraversingParser(new BigIntegerNode(big));
        assertToken(JsonToken.VALUE_NUMBER_INT, p.nextToken());
        try {
            p.getIntValue();
            fail("Expected failure for `int` overflow");
        } catch (JsonParseException e) {
            verifyException(e, "Numeric value (");
            verifyException(e, ") out of range of int");
        }
        p.close();
    }
}