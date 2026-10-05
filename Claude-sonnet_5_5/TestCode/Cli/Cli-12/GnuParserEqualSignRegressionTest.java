package org.apache.commons.cli;

import junit.framework.TestCase;

public class GnuParserEqualSignRegressionTest extends TestCase
{
    private Options createOptions()
    {
        Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("foo")
                                       .hasArg()
                                       .create('f'));
        return options;
    }

    public void testShortWithEqual() throws Exception
    {
        String[] args = new String[] { "-f=bar" };

        Parser parser = new GnuParser();
        CommandLine cl = parser.parse(createOptions(), args);

        assertEquals("bar", cl.getOptionValue("foo"));
        assertEquals("bar", cl.getOptionValue("f"));
    }

    public void testLongWithEqual() throws Exception
    {
        String[] args = new String[] { "--foo=bar" };

        Parser parser = new GnuParser();
        CommandLine cl = parser.parse(createOptions(), args);

        assertEquals("bar", cl.getOptionValue("foo"));
    }

    public void testLongWithEqualSingleDash() throws Exception
    {
        String[] args = new String[] { "-foo=bar" };

        Parser parser = new GnuParser();
        CommandLine cl = parser.parse(createOptions(), args);

        assertEquals("bar", cl.getOptionValue("foo"));
    }
}