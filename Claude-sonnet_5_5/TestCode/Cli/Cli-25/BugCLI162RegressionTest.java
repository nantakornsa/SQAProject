package org.apache.commons.cli.bug;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Regression test for CLI-162: when nextLineTabStop is greater than or equal
 * to the width, the continuation indent must be reset to 1 instead of width - 1.
 */
public class BugCLI162RegressionTest extends TestCase
{
    public void testLongLineChunkingIndentIgnored() throws ParseException, IOException
    {
        Options options = new Options();
        options.addOption("x", "extralongarg", false, "This description is Long.");
        HelpFormatter formatter = new HelpFormatter();
        StringWriter sw = new StringWriter();
        formatter.printHelp(new PrintWriter(sw), 22,
                            "org.apache.commons.cli.bug.BugCLI162Test",
                            "Header", options, 0, 5, "Footer");
        String expected = "usage:\n" +
                          "       org.apache.comm\n" +
                          "       ons.cli.bug.Bug\n" +
                          "       CLI162Test\n" +
                          "Header\n" +
                          "-x,--extralongarg\n" +
                          " This description is\n" +
                          " Long.\n" +
                          "Footer\n";
        assertEquals("Long arguments did not split as expected", expected, sw.toString());
    }
}