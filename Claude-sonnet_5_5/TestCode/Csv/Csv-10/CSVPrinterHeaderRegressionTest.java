package org.apache.commons.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

public class CSVPrinterHeaderRegressionTest {

    @Test
    public void testHeaderIsPrintedOnCreation() throws IOException {
        final StringWriter sw = new StringWriter();
        final CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT.withQuoteChar(null)
                .withHeader("C1", "C2", "C3"));
        printer.printRecord("a", "b", "c");
        printer.printRecord("x", "y", "z");
        assertEquals("C1,C2,C3\r\na,b,c\r\nx,y,z\r\n", sw.toString());
        printer.close();
    }

    @Test
    public void testHeaderOnlyIsPrintedWhenNoRecords() throws IOException {
        final StringWriter sw = new StringWriter();
        final CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader("H1", "H2"));
        assertEquals("H1,H2\r\n", sw.toString());
        printer.close();
    }
}