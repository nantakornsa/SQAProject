package com.fasterxml.jackson.core.read;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.BaseTest;

public class NonStandardUnquotedNamesTest extends BaseTest {

    public void testUnquotedIssue510() throws Exception {
        JsonFactory f = new JsonFactory();
        f.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // Character 256 (\u0100) triggers ArrayIndexOutOfBoundsException when maxCode == 256
        String json = "{\u0100:123}";
        try (JsonParser p = f.createParser(new java.io.StringReader(json))) {
            assertToken(JsonToken.START_OBJECT, p.nextToken());
            assertToken(JsonToken.FIELD_NAME, p.nextToken());
            assertEquals("\u0100", p.getCurrentName());
            assertToken(JsonToken.VALUE_NUMBER_INT, p.nextToken());
            assertEquals(123, p.getIntValue());
            assertToken(JsonToken.END_OBJECT, p.nextToken());
        }
    }
}