package org.apache.commons.csv;

import junit.framework.TestCase;

/**
 * Regression test for CSV-75: the line number must be incremented when
 * a lone carriage return is used as the line separator.
 */
public class CSVParserLineNumberCRRegressionTest extends TestCase {

    public void testGetLineNumberWithCR() throws Exception {
        CSVParser parser = new CSVParser("a\rb\rc", CSVFormat.DEFAULT.withLineSeparator("\r"));

        assertEquals(0, parser.getLineNumber());
        assertNotNull(parser.getRecord());
        assertEquals(1, parser.getLineNumber());
        assertNotNull(parser.getRecord());
        assertEquals(2, parser.getLineNumber());
        assertNotNull(parser.getRecord());
        assertEquals(2, parser.getLineNumber());
        assertNull(parser.getRecord());
    }
}