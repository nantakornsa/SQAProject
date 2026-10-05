package org.apache.commons.compress.compressors.bzip2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Test;

public class GeminiBZip2CompressorInputStreamTest {

    @Test
    public void testReadOfLength0ShouldReturn0() throws Exception {
        byte[] rawData = new byte[2048];
        for (int i = 0; i < rawData.length; ++i) {
            rawData[i] = (byte) (i & 0xff);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BZip2CompressorOutputStream bzipOut = new BZip2CompressorOutputStream(baos);
        bzipOut.write(rawData);
        bzipOut.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BZip2CompressorInputStream bzipIn = new BZip2CompressorInputStream(bais);
        try {
            byte[] buffer = new byte[1024];
            Assert.assertEquals(1024, bzipIn.read(buffer, 0, 1024));
            Assert.assertEquals(0, bzipIn.read(buffer, 1024, 0));
            Assert.assertEquals(1024, bzipIn.read(buffer, 0, 1024));
        } finally {
            bzipIn.close();
        }
    }
}