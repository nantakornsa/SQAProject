package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CSVFormatDuplicateHeaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateHeaderElementsThrowIllegalArgumentException() {
        CSVFormat.DEFAULT.withHeader("A", "A").validate();
    }

    @Test
    public void testDuplicateHeaderRejectedOnCreation() {
        try {
            CSVFormat.DEFAULT.withHeader("A", "B", "A");
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // expected: duplicate detected when the format is constructed
        }
    }

    @Test
    public void testDistinctHeaderElementsAccepted() {
        final CSVFormat format = CSVFormat.DEFAULT.withHeader("A", "B");
        format.validate();
        assertEquals(2, format.getHeader().length);
    }
}