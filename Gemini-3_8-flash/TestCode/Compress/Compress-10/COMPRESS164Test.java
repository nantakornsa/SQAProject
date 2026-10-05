package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.Test;

public class COMPRESS164Test {

    @Test
    public void testReadWinZipArchive() throws Exception {
        URL zip = getClass().getResource("/utf8-winzip-test.zip");
        File archive = new File(new URI(zip.toString()));
        ZipFile zf = null;
        try {
            zf = new ZipFile(archive, null, true);
            ZipArchiveEntry ze = zf.getEntry("\u20AC_for_Dollar.txt");
            assertNotNull("Entry should be found", ze);
            InputStream is = zf.getInputStream(ze);
            try {
                assertNotNull("InputStream should not be null", is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } finally {
            ZipFile.closeQuietly(zf);
        }
    }
}