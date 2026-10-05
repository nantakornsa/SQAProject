package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.junit.Test;

public class CSVParserIteratorSequenceRegressionTest {

    @Test
    public void testIteratorSequenceBreaking() throws IOException {
        final String fiveRows = "1\n2\n3\n4\n5\n";

        // Peeking with hasNext() on a fresh iterator must not lose a record
        CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(fiveRows));
        int recordNumber = 0;
        for (final CSVRecord record : parser) {
            recordNumber++;
            assertEquals(String.valueOf(recordNumber), record.get(0));
            if (recordNumber >= 2) {
                break;
            }
        }
        assertTrue(parser.iterator().hasNext());
        for (final CSVRecord record : parser) {
            recordNumber++;
            assertEquals(String.valueOf(recordNumber), record.get(0));
        }
        assertEquals(5, recordNumber);
        parser.close();

        // Consecutive enhanced for loops must not break the sequence
        parser = CSVFormat.DEFAULT.parse(new StringReader(fiveRows));
        recordNumber = 0;
        for (final CSVRecord record : parser) {
            recordNumber++;
            assertEquals(String.valueOf(recordNumber), record.get(0));
            if (recordNumber >= 2) {
                break;
            }
        }
        for (final CSVRecord record : parser) {
            recordNumber++;
            assertEquals(String.valueOf(recordNumber), record.get(0));
        }
        assertEquals(5, recordNumber);
        parser.close();

        // The same iterator instance should be returned each time
        parser = CSVFormat.DEFAULT.parse(new StringReader(fiveRows));
        final Iterator<CSVRecord> first = parser.iterator();
        assertSame(first, parser.iterator());
        parser.close();
    }
}