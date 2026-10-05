package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Assert;
import org.junit.Test;

public class ByteQuadsCanonicalizerRegressionTest {

    @Test
    public void testIssue207() throws Exception {
        final JsonFactory jsonFactory = new JsonFactory();
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < 2000; ++i) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append('"').append("field_").append(i).append("\":").append(i);
        }
        sb.append('}');

        final byte[] jsonBytes = sb.toString().getBytes("UTF-8");

        JsonParser parser = jsonFactory.createParser(jsonBytes);
        int count = 0;
        while (parser.nextToken() != null) {
            if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                count++;
            }
        }
        parser.close();

        Assert.assertEquals(2000, count);
    }
}