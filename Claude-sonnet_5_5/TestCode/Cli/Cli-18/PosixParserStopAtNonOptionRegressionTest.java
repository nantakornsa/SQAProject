package org.apache.commons.cli;

import junit.framework.TestCase;

public class PosixParserStopAtNonOptionRegressionTest extends TestCase
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

    public void testStop2() throws Exception
    {
        String[] args = new String[] { "-z", "-a", "-btoast" };

        CommandLine cl = parser.parse(options, args, true);
        assertFalse("Confirm -a is not set", cl.hasOption("a"));
        assertTrue("Confirm 3 extra args: " + cl.getArgList().size(), cl.getArgList().size() == 3);
    }
}