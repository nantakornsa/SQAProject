package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

/**
 * Regression test for CSV-106: a null record separator must not be printed as the string "null".
 */
public class CSVPrinterNullRecordSeparatorTest {

    @Test
    public void testNullRecordSeparatorCsv106() {
        final CSVFormat format = CSVFormat.newFormat(';').withSkipHeaderRecord(true).withHeader("H1", "H2");
        final String formatStr = format.format("A", "B");
        assertNotNull(formatStr);
        assertFalse(formatStr.endsWith("null"));
        assertEquals("A;B", formatStr);
    }

    @Test
    public void testPrintlnWithNullRecordSeparator() throws IOException {
        final StringBuilder sb = new StringBuilder();
        final CSVPrinter printer = new CSVPrinter(sb, CSVFormat.newFormat(';'));
        printer.print("a");
        printer.print("b");
        printer.println();
        printer.print("c");
        printer.close();
        assertFalse(sb.toString().contains("null"));
        assertEquals("a;bc", sb.toString());
    }
}