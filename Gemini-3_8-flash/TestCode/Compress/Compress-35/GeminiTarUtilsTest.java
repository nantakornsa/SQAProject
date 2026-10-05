package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class GeminiTarUtilsTest {

    @Test
    public void testVerifyCheckSumWithSevenDigitOctal() {
        byte[] header = new byte[512];
        for (int i = 0; i < header.length; i++) {
            header[i] = 0;
        }

        // Fill checksum field with spaces to calculate the checksum
        for (int i = TarConstants.CHKSUM_OFFSET; i < TarConstants.CHKSUM_OFFSET + TarConstants.CHKSUMLEN; i++) {
            header[i] = ' ';
        }

        // Add some dummy content to ensure the checksum exceeds 6 octal digits (e.g. >= 01000000 octal = 262144)
        // or has 7 significant octal digits with leading zeros (e.g. "0123456\0")
        // Let's set a specific 7-digit checksum: "0123456\0" (octal 0123456 = 42798)
        long targetSum = 0123456L;

        // Current sum with 8 spaces in checksum field: 8 * ' ' = 8 * 32 = 256
        long currentSum = 8 * ' ';
        long remaining = targetSum - currentSum;

        // Distribute 'remaining' across header bytes outside the checksum field
        for (int i = 0; i < TarConstants.CHKSUM_OFFSET && remaining > 0; i++) {
            int add = (int) Math.min(255, remaining);
            header[i] = (byte) add;
            remaining -= add;
        }

        // Write "0123456\0" into checksum field
        String checksumString = "0123456\0";
        for (int i = 0; i < checksumString.length(); i++) {
            header[TarConstants.CHKSUM_OFFSET + i] = (byte) checksumString.charAt(i);
        }

        assertTrue("verifyCheckSum should return true for 7-digit octal checksum",
                TarUtils.verifyCheckSum(header));
    }
}