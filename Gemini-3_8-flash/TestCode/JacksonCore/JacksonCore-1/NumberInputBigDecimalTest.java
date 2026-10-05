package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.Assert;
import org.junit.Test;

public class NumberInputBigDecimalTest {

    @Test
    public void testNaNToBigDecimalExceptionMessage() throws Exception {
        JsonFactory f = new JsonFactory();
        f.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);

        for (boolean useBytes : new boolean[] { false, true }) {
            JsonParser jp = useBytes
                    ? f.createParser("NaN".getBytes("UTF-8"))
                    : f.createParser("NaN");

            Assert.assertEquals(JsonToken.VALUE_NUMBER_FLOAT, jp.nextToken());
            try {
                jp.getDecimalValue();
                Assert.fail("Should have thrown NumberFormatException");
            } catch (NumberFormatException e) {
                Assert.assertNotNull("Exception message should not be null", e.getMessage());
                Assert.assertTrue("Exception message should contain 'can not be represented as BigDecimal' but was: " + e.getMessage(),
                        e.getMessage().contains("can not be represented as BigDecimal"));
            }
            jp.close();
        }
    }
}