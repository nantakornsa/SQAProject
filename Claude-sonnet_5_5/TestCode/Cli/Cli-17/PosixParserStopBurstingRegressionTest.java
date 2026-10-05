package org.apache.commons.cli;

import junit.framework.TestCase;

public class PosixParserStopBurstingRegressionTest extends TestCase
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

    public void testStopBursting() throws Exception
    {
        String[] args = new String[] { "-azc" };

        CommandLine cl = parser.parse(options, args, true);
        assertTrue("Confirm -a is set", cl.hasOption("a"));
        assertFalse("Confirm -c is not set", cl.hasOption("c"));

        assertTrue("Confirm  1 extra arg: " + cl.getArgList().size(), cl.getArgList().size() == 1);
        assertTrue("Confirm remaining arg is zc", cl.getArgList().contains("zc"));
    }
}