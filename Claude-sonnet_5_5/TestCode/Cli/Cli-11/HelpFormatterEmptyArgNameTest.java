package org.apache.commons.cli;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

/**
 * Regression test: HelpFormatter must not print an argument placeholder
 * ("<>") when an option's argument name is an empty string.
 */
public class HelpFormatterEmptyArgNameTest extends TestCase
{
    private static final String EOL = System.getProperty("line.separator");

    public void testPrintOptionWithEmptyArgNameUsage()
    {
        Option option = new Option("f", true, null);
        option.setArgName("");
        option.setRequired(true);

        Options options = new Options();
        options.addOption(option);

        StringWriter out = new StringWriter();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printUsage(new PrintWriter(out), 80, "app", options);

        assertEquals("usage: app -f" + EOL, out.toString());
    }
}