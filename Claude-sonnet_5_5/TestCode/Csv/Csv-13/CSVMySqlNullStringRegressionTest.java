package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * Regression test for CSV-168: the MySQL format should use "\N" as its default null string.
 */
public class CSVMySqlNullStringRegressionTest {

    @Test
    public void testMySqlNullStringDefault() {
        assertEquals("\\N", CSVFormat.MYSQL.getNullString());
    }

    @Test
    public void testMySqlNullOutputDefault() throws IOException {
        final StringWriter writer = new StringWriter();
        final CSVPrinter printer = new CSVPrinter(writer, CSVFormat.MYSQL);
        printer.printRecord(new Object[] { "", null });
        printer.close();
        assertEquals("\t\\N\n", writer.toString());
    }

    @Test
    public void testMySqlNullOutputWithQuoteAndCustomNullString() throws IOException {
        final CSVFormat format = CSVFormat.MYSQL.withQuote('"').withNullString("NULL")
                .withQuoteMode(QuoteMode.NON_NUMERIC);
        final StringWriter writer = new StringWriter();
        final CSVPrinter printer = new CSVPrinter(writer, format);
        printer.printRecord(new Object[] { "NULL", null });
        printer.close();
        // A real null must not be quoted, while the string "NULL" must be.
        assertEquals("\"NULL\"\tNULL\n", writer.toString());
    }
}