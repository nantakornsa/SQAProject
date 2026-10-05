package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

public class CSVPrinterEuroFirstCharRegressionTest {

    private static final String EURO_CH = "\u20AC";

    @Test
    public void testDontQuoteEuroFirstChar() throws IOException {
        final StringWriter sw = new StringWriter();
        try (final CSVPrinter printer = new CSVPrinter(sw, CSVFormat.RFC4180)) {
            printer.printRecord(EURO_CH, "Deux");
            assertEquals(EURO_CH + ",Deux" + CSVFormat.RFC4180.getRecordSeparator(), sw.toString());
        }
    }

    @Test
    public void testDontQuoteNonAsciiFirstCharMultipleRecords() throws IOException {
        final StringWriter sw = new StringWriter();
        try (final CSVPrinter printer = new CSVPrinter(sw, CSVFormat.RFC4180)) {
            printer.printRecord(EURO_CH, "Deux");
            printer.printRecord("\u00E9t\u00E9", "x");
            final String sep = CSVFormat.RFC4180.getRecordSeparator();
            assertEquals(EURO_CH + ",Deux" + sep + "\u00E9t\u00E9,x" + sep, sw.toString());
        }
    }
}