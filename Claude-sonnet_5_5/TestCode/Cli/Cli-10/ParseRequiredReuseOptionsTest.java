package org.apache.commons.cli;

import junit.framework.TestCase;

public class ParseRequiredReuseOptionsTest extends TestCase
{
    public void testReuseOptionsTwice() throws Exception
    {
        Options opts = new Options();
        opts.addOption(OptionBuilder.isRequired().create('v'));

        GnuParser parser = new GnuParser();

        // first parse: required option present, should succeed
        CommandLine cmd = parser.parse(opts, new String[] { "-v" });
        assertTrue(cmd.hasOption("v"));

        // second parse with the same Options: required option missing
        try
        {
            parser.parse(opts, new String[0]);
            fail("MissingOptionException not thrown");
        }
        catch (MissingOptionException e)
        {
            // expected
        }
    }

    public void testReuseOptionsTwicePosixParser() throws Exception
    {
        Options opts = new Options();
        opts.addOption(OptionBuilder.isRequired().create('v'));

        PosixParser parser = new PosixParser();

        CommandLine cmd = parser.parse(opts, new String[] { "-v" });
        assertTrue(cmd.hasOption("v"));

        try
        {
            parser.parse(opts, new String[0]);
            fail("MissingOptionException not thrown");
        }
        catch (MissingOptionException e)
        {
            // expected
        }
    }
}