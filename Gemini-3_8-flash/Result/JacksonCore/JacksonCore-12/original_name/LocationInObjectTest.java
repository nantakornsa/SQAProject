package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class LocationInObjectTest {

    @Test
    public void testOffsetWithObjectFieldsUsingReader() throws Exception {
        JsonFactory f = new JsonFactory();
        final String DOC = "{ \"a\": 1, \"b\": 2 }";

        JsonParser p = f.createParser(new StringReader(DOC));

        Assert.assertEquals(JsonToken.START_OBJECT, p.nextToken());
        Assert.assertEquals(0, p.getTokenLocation().getCharOffset());

        Assert.assertEquals(JsonToken.FIELD_NAME, p.nextToken());
        Assert.assertEquals("a", p.getCurrentName());
        Assert.assertEquals(2, p.getTokenLocation().getCharOffset());

        Assert.assertEquals(JsonToken.VALUE_NUMBER_INT, p.nextToken());
        Assert.assertEquals(1, p.getIntValue());
        Assert.assertEquals(7, p.getTokenLocation().getCharOffset());

        Assert.assertEquals(JsonToken.FIELD_NAME, p.nextToken());
        Assert.assertEquals("b", p.getCurrentName());
        Assert.assertEquals(11, p.getTokenLocation().getCharOffset());

        Assert.assertEquals(JsonToken.VALUE_NUMBER_INT, p.nextToken());
        Assert.assertEquals(2, p.getIntValue());
        Assert.assertEquals(16, p.getTokenLocation().getCharOffset());

        Assert.assertEquals(JsonToken.END_OBJECT, p.nextToken());

        p.close();
    }
}