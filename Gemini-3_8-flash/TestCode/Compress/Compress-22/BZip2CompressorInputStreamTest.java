package org.apache.commons.compress.compressors.bzip2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

public class BZip2CompressorInputStreamTest {

    @Test
    public void testPartialReadTruncatedData() throws IOException {
        byte[] data = "Hello, world! This is a test string for bzip2 compression truncation.".getBytes("US-ASCII");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BZip2CompressorOutputStream out = new BZip2CompressorOutputStream(baos);
        out.write(data);
        out.close();

        byte[] compressed = baos.toByteArray();
        // Truncate the stream by removing trailer bytes (the end-of-stream magic and CRC)
        // Keep enough data to decompress the block, but truncate the stream ending
        byte[] truncated = Arrays.copyOf(compressed, compressed.length - 10);

        BZip2CompressorInputStream in = new BZip2CompressorInputStream(new ByteArrayInputStream(truncated));
        byte[] buffer = new byte[data.length];
        int totalRead = 0;
        while (totalRead < data.length) {
            int read = in.read(buffer, totalRead, data.length - totalRead);
            if (read == -1) {
                break;
            }
            totalRead += read;
        }

        // On the buggy version, reading the last byte eagerly triggers setup of the next block/trailer,
        // causing an unexpected end of stream IOException during reading the original data.
        assertArrayEquals(data, buffer);

        // Subsequent read after reading all valid data should throw IOException
        try {
            in.read();
            fail("Expected IOException on reading past truncated data");
        } catch (IOException e) {
            // expected
        } finally {
            in.close();
        }
    }
}