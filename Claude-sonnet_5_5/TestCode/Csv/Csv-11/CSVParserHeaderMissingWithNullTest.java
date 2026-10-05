package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class CSVParserHeaderMissingWithNullTest {

    @Test
    public void testHeaderMissingWithNullRegression() throws Exception {
        final Reader in = new StringReader("a,,c,,d\n1,2,3,4\nx,y,z,zz");
        final CSVParser parser = CSVFormat.DEFAULT.withHeader().withNullString("")
                .withIgnoreEmptyHeaders(true).parse(in);
        try {
            final Map<String, Integer> headerMap = parser.getHeaderMap();
            assertTrue(headerMap.containsKey("a"));
            assertTrue(headerMap.containsKey("c"));
            assertTrue(headerMap.containsKey("d"));
            assertFalse(headerMap.containsKey(null));
            assertFalse(headerMap.containsKey(""));
            assertEquals(3, headerMap.size());

            final Iterator<CSVRecord> iterator = parser.iterator();
            assertTrue(iterator.hasNext());
            final CSVRecord record = iterator.next();
            assertEquals("1", record.get("a"));
            assertEquals("3", record.get("c"));
        } finally {
            parser.close();
        }
    }
}