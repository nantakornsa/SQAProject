package org.apache.commons.cli.bug;

import junit.framework.TestCase;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.UnrecognizedOptionException;

/**
 * Regression test for CLI-51: PosixParser.burstToken must keep the original
 * token when it meets an unrecognized character, instead of splitting it into
 * a bogus "-x" token.
 */
public class ClaudeBugCLI51Test extends TestCase
{
    public void test() throws Exception
    {
        Options options = new Options();
        options.addOption("v", false, "verbose");

        Parser parser = new PosixParser();

        try
        {
            CommandLine cmd = parser.parse(options, new String[] { "-vx" });
            fail("UnrecognizedOptionException expected, but parsed: " + cmd);
        }
        catch (UnrecognizedOptionException e)
        {
            // The whole original token must be reported as unrecognized,
            // not the single burst character "-x".
            assertEquals("Unrecognized option: -vx", e.getMessage());
        }
    }
}