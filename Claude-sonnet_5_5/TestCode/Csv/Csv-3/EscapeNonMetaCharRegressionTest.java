package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

/**
 * Regression test for CSV-58: an escape character followed by a character that
 * is not a meta-character must keep both the escape character and the character.
 */
public class EscapeNonMetaCharRegressionTest {

    @Test
    public void testEscapedMySqlNullValueIsPreserved() throws Exception {
        // MySQL uses \N to symbolize null values. It must be preserved as-is.
        final CSVFormat format = CSVFormat.DEFAULT.withEscape('\\');
        final CSVParser parser = new CSVParser(new StringReader("character\\NEscaped"), format);
        try {
            final List<CSVRecord> records = parser.getRecords();
            assertEquals(1, records.size());
            assertEquals("character\\NEscaped", records.get(0).get(0));
        } finally {
            parser.close();
        }
    }

    @Test
    public void testEscapedMetaCharacterIsUnescaped() throws Exception {
        // An escaped delimiter must still be unescaped
        final CSVFormat format = CSVFormat.DEFAULT.withEscape('\\');
        final CSVParser parser = new CSVParser(new StringReader("a\\,b,c"), format);
        try {
            final List<CSVRecord> records = parser.getRecords();
            assertEquals(1, records.size());
            assertEquals("a,b", records.get(0).get(0));
            assertEquals("c", records.get(0).get(1));
        } finally {
            parser.close();
        }
    }
}