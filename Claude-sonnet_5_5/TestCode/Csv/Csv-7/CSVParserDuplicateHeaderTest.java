package org.apache.commons.csv;

import org.junit.Test;

public class CSVParserDuplicateHeaderTest {

    @Test(expected = IllegalStateException.class)
    public void testDuplicateHeaderEntries() throws Exception {
        CSVParser.parse("a,b,a\n1,2,3\nx,y,z", CSVFormat.DEFAULT.withHeader(new String[]{}));
    }
}