package org.apache.commons.cli;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

public class HelpFormatterTest extends TestCase
{
    private static final String EOL = System.getProperty("line.separator");

    public void testDefaultArgName()
    {
        Option option = OptionBuilder.hasArg().isRequired().create("f");

        Options options = new Options();
        options.addOption(option);

        StringWriter out = new StringWriter();

        HelpFormatter formatter = new HelpFormatter();
        formatter.setArgName("argument");
        formatter.printUsage(new PrintWriter(out), 80, "app", options);

        assertEquals("usage: app -f <argument>" + EOL, out.toString());
    }
}