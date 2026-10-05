package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.test.BaseTest;

import java.io.ByteArrayInputStream;
import java.io.StringReader;

public class TestRootSpaceHandling extends BaseTest {

    private final JsonFactory FACTORY = new JsonFactory();

    public void testMangledNumbersBytes() throws Exception {
        _testMangledNumbers(true);
    }

    public void testMangledNumbersChars() throws Exception {
        _testMangledNumbers(false);
    }

    private void _testMangledNumbers(boolean useBytes) throws Exception {
        String doc = "123a";
        JsonParser jp = useBytes
                ? FACTORY.createParser(new ByteArrayInputStream(doc.getBytes("UTF-8")))
                : FACTORY.createParser(new StringReader(doc));
        try {
            JsonToken t = jp.nextToken();
            fail("Should have gotten an exception; instead got token: " + t);
        } catch (JsonParseException e) {
            verifyException(e, "expected space");
        } finally {
            jp.close();
        }
    }
}