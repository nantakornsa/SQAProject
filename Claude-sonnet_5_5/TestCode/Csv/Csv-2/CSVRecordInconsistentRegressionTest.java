package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CSVRecordInconsistentRegressionTest {

    @Test
    public void testGetByNameWithHeaderIndexOutOfRangeThrowsIllegalArgument() throws Exception {
        final CSVFormat format = CSVFormat.DEFAULT.withHeader("first", "second", "third", "fourth");
        final CSVParser parser = new CSVParser("a,b\n", format);
        try {
            final CSVRecord record = parser.iterator().next();
            assertEquals("a", record.get("first"));
            assertEquals("b", record.get("second"));
            try {
                record.get("fourth");
                fail("Expected IllegalArgumentException");
            } catch (final IllegalArgumentException expected) {
                // expected: header maps to an index beyond the record's values
            } catch (final ArrayIndexOutOfBoundsException e) {
                fail("Unexpected ArrayIndexOutOfBoundsException: " + e);
            }
        } finally {
            parser.close();
        }
    }
}