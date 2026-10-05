package com.fasterxml.jackson.core.json;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class UTF8JsonGeneratorRawSurrogatesTest {

    @Test
    public void testRawWithSurrogatesString() throws Exception {
        JsonFactory f = new JsonFactory();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Construct a surrogate pair: Emoji grinning face U+1F600 (\uD83D\uDE00)
        String surrogatePair = "\uD83D\uDE00";

        // Generate a string large enough to exceed generator buffer sizes,
        // testing various alignments so that a surrogate pair is split across chunk boundaries.
        for (int padding = 0; padding < 4; padding++) {
            out.reset();
            JsonGenerator gen = f.createGenerator(out);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < padding; i++) {
                sb.append('a');
            }
            for (int i = 0; i < 4000; i++) {
                sb.append(surrogatePair);
            }
            String rawString = sb.toString();

            gen.writeStartArray();
            gen.writeRaw(rawString);
            gen.writeEndArray();
            gen.close();

            // Verify output matches expected UTF-8 bytes
            ByteArrayOutputStream expectedOut = new ByteArrayOutputStream();
            expectedOut.write('[');
            expectedOut.write(rawString.getBytes("UTF-8"));
            expectedOut.write(']');

            assertArrayEquals(expectedOut.toByteArray(), out.toByteArray());
        }
    }
}