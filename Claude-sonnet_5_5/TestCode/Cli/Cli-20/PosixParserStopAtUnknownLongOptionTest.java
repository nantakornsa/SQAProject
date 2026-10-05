package org.apache.commons.cli;

import junit.framework.TestCase;

public class PosixParserStopAtUnknownLongOptionTest extends TestCase
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

    public void testStopAtUnknownLongOptionWithEquals() throws Exception
    {
        String[] args = new String[] { "--zop==1", "-abtoast", "--b=bar" };

        CommandLine cl = parser.parse(options, args, true);

        assertFalse("Confirm -a is not set", cl.hasOption("a"));
        assertFalse("Confirm -b is not set", cl.hasOption("b"));
        assertEquals("Confirm  3 extra args: " + cl.getArgList().size(), 3, cl.getArgList().size());
    }
}