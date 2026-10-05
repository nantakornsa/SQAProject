package org.apache.commons.cli;

import java.util.Properties;

import junit.framework.TestCase;

public class PropertyOptionFlagsRegressionTest extends TestCase
{
    public void testPropertyOptionFlagsFalseValueDoesNotStopProcessing() throws Exception
    {
        Options opts = new Options();
        opts.addOption("a", false, "toggle a");
        opts.addOption("b", false, "toggle b");
        opts.addOption("c", false, "toggle c");
        opts.addOption("d", false, "toggle d");
        opts.addOption("e", false, "toggle e");
        opts.addOption("f", false, "toggle f");
        opts.addOption("g", false, "toggle g");

        Properties properties = new Properties();
        properties.setProperty("a", "true");
        properties.setProperty("b", "false");
        properties.setProperty("c", "yes");
        properties.setProperty("d", "0");
        properties.setProperty("e", "1");
        properties.setProperty("f", "no");
        properties.setProperty("g", "TRUE");

        Parser parser = new GnuParser();
        CommandLine cmd = parser.parse(opts, new String[0], properties);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertTrue(cmd.hasOption("c"));
        assertFalse(cmd.hasOption("d"));
        assertTrue(cmd.hasOption("e"));
        assertFalse(cmd.hasOption("f"));
        assertTrue(cmd.hasOption("g"));
    }

    public void testPropertyOptionFlagsWithBasicParser() throws Exception
    {
        Options opts = new Options();
        opts.addOption("a", false, "toggle a");
        opts.addOption("b", false, "toggle b");
        opts.addOption("c", false, "toggle c");

        Properties properties = new Properties();
        properties.setProperty("a", "true");
        properties.setProperty("b", "false");
        properties.setProperty("c", "yes");

        Parser parser = new BasicParser();
        CommandLine cmd = parser.parse(opts, new String[0], properties);

        assertTrue(cmd.hasOption("a"));
        assertFalse(cmd.hasOption("b"));
        assertTrue(cmd.hasOption("c"));
    }
}