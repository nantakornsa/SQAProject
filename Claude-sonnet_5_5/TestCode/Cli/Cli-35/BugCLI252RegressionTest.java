package org.apache.commons.cli.bug;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class BugCLI252RegressionTest extends TestCase
{
    private Options getOptions()
    {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("prefix").build());
        options.addOption(Option.builder().longOpt("prefixplusplus").build());
        return options;
    }

    public void testExactOptionNameMatchDoesNotThrowAmbiguous() throws ParseException
    {
        CommandLine cmd = new DefaultParser().parse(getOptions(), new String[]{"--prefix"});
        assertTrue(cmd.hasOption("prefix"));
        assertFalse(cmd.hasOption("prefixplusplus"));
    }

    public void testGetMatchingOptionsExactMatch()
    {
        List<String> matches = getOptions().getMatchingOptions("prefix");
        assertEquals(1, matches.size());
        assertEquals("prefix", matches.get(0));
    }

    public void testGetMatchingOptionsPartialMatchStillReturnsAll()
    {
        List<String> matches = getOptions().getMatchingOptions("pre");
        assertEquals(2, matches.size());
        assertTrue(matches.contains("prefix"));
        assertTrue(matches.contains("prefixplusplus"));
    }
}