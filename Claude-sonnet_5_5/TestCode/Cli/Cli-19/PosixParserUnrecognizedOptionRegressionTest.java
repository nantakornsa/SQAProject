package org.apache.commons.cli;

import junit.framework.TestCase;

public class PosixParserUnrecognizedOptionRegressionTest extends TestCase
{
    private Options options;
    private Parser parser;

    public void setUp()
    {
        options = new Options()
            .addOption("a", "enable-a", false, "turn [a] on or off")
            .addOption("b", "bfile", true, "set the value of [b]")
            .addOption("c", "copt", false, "turn [c] on or off");

        parser = new PosixParser();
    }

    public void testUnrecognizedOption2() throws Exception
    {
        String[] args = new String[] { "-z", "-abtoast", "foo", "bar" };

        try
        {
            parser.parse(options, args);
            fail("UnrecognizedOptionException wasn't thrown");
        }
        catch (UnrecognizedOptionException e)
        {
            assertEquals("-z", e.getOption());
        }
    }
}