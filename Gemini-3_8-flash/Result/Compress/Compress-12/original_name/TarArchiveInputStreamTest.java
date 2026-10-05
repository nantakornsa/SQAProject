package org.apache.commons.compress.archivers.tar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TarArchiveInputStreamTest {

    @Test
    public void testInvalidHeaderThrowsIOExceptionInsteadOfIllegalArgumentException() {
        byte[] record = new byte[512];
        // Populate entry name so it is not treated as EOF record
        record[0] = 'a';
        // Populate invalid byte in mode field (offset 100, length 8)
        record[100] = (byte) 255;

        TarArchiveInputStream in = new TarArchiveInputStream(new ByteArrayInputStream(record));
        try {
            in.getNextTarEntry();
            fail("Expected IOException");
        } catch (IOException e) {
            assertTrue("Expected cause to be IllegalArgumentException",
                    e.getCause() instanceof IllegalArgumentException);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should have been wrapped in an IOException");
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
            }
        }
    }
}