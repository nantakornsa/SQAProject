package org.apache.commons.compress.archivers.zip;

import org.junit.Test;

import java.util.zip.ZipException;

import static org.junit.Assert.assertEquals;

public class GeminiZip64ExtendedInformationExtraFieldTest {

    @Test
    public void testReparseCentralDirectoryDataWithExcessData() throws ZipException {
        Zip64ExtendedInformationExtraField extraField = new Zip64ExtendedInformationExtraField();

        // 28 bytes of data (e.g. 8 bytes uncompressed size, 8 bytes compressed size, plus 12 excess bytes)
        byte[] data = new byte[] {
            1, 0, 0, 0, 0, 0, 0, 0, // uncompressed size = 1
            2, 0, 0, 0, 0, 0, 0, 0, // compressed size = 2
            0, 0, 0, 0, 0, 0, 0, 0, // excess bytes
            0, 0, 0, 0
        };

        extraField.parseFromCentralDirectoryData(data, 0, data.length);
        // expected length is 16 bytes (8 + 8), but raw data length is 28
        extraField.reparseCentralDirectoryData(true, true, false, false);

        assertEquals(new ZipEightByteInteger(1), extraField.getSize());
        assertEquals(new ZipEightByteInteger(2), extraField.getCompressedSize());
    }
}