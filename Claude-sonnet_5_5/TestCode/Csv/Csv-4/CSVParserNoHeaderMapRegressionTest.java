package org.apache.commons.csv;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for CSV-100: getHeaderMap() must not throw a NullPointerException
 * when no header was configured.
 */
public class CSVParserNoHeaderMapRegressionTest {

    @Test
    public void testNoHeaderMap() throws Exception {
        final CSVParser parser = CSVParser.parse("a,b,c\n1,2,3\nx,y,z", CSVFormat.DEFAULT);
        try {
            Assert.assertNull(parser.getHeaderMap());
        } finally {
            parser.close();
        }
    }
}