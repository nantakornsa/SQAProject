package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Map;

import org.junit.Test;

public class CSVRecordShortRecordRegressionTest {

    @Test
    public void testToMapWithShortRecordSkipsMissingColumns() throws Exception {
        final CSVParser parser = CSVParser.parse("a,b", CSVFormat.DEFAULT.withHeader("A", "B", "C"));
        try {
            final CSVRecord shortRec = parser.iterator().next();
            final Map<String, String> map = shortRec.toMap();
            assertEquals(2, map.size());
            assertEquals("a", map.get("A"));
            assertEquals("b", map.get("B"));
            assertFalse(map.containsKey("C"));
        } finally {
            parser.close();
        }
    }
}