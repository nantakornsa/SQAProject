package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CSVRecordNoHeaderRegressionTest {

    @Test
    public void testToMapWithNoHeader() throws Exception {
        final CSVParser parser = CSVParser.parse("a,b", CSVFormat.newFormat(','));
        try {
            final CSVRecord rec = parser.iterator().next();
            final Map<String, String> map = rec.toMap();
            assertNotNull("Map is not null.", map);
            assertTrue("Map is empty.", map.isEmpty());
        } finally {
            parser.close();
        }
    }

    @Test
    public void testPutInWithNoHeaderLeavesMapUntouched() throws Exception {
        final CSVParser parser = CSVParser.parse("a,b", CSVFormat.newFormat(','));
        try {
            final CSVRecord rec = parser.iterator().next();
            final Map<String, String> target = new HashMap<String, String>();
            final Map<String, String> result = rec.putIn(target);
            assertEquals(0, result.size());
        } finally {
            parser.close();
        }
    }
}