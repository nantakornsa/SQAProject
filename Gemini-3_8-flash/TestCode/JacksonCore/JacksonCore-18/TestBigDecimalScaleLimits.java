package com.fasterxml.jackson.core.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.test.BaseTest;

public class TestBigDecimalScaleLimits extends BaseTest
{
    public void testTooBigBigDecimalWriter() throws Exception
    {
        JsonFactory f = new JsonFactory();
        f.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

        BigDecimal big = new BigDecimal("1E+10000");

        StringWriter sw = new StringWriter();
        JsonGenerator g = f.createGenerator(sw);
        try {
            g.writeNumber(big);
            g.flush();
            fail("Should not have written without exception: " + sw.toString());
        } catch (JsonGenerationException e) {
            verifyException(e, "Attempt to write plain `java.math.BigDecimal`");
            verifyException(e, "illegal scale");
        } finally {
            g.close();
        }
    }

    public void testTooBigBigDecimalUTF8() throws Exception
    {
        JsonFactory f = new JsonFactory();
        f.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

        BigDecimal big = new BigDecimal("1E+10000");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        JsonGenerator g = f.createGenerator(bytes);
        try {
            g.writeNumber(big);
            g.flush();
            fail("Should not have written without exception: " + bytes.toString("UTF-8"));
        } catch (JsonGenerationException e) {
            verifyException(e, "Attempt to write plain `java.math.BigDecimal`");
            verifyException(e, "illegal scale");
        } finally {
            g.close();
        }
    }
}