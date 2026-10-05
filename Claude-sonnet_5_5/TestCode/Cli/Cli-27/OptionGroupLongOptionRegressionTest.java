package org.apache.commons.cli;

import junit.framework.TestCase;

/**
 * Regression test for CLI-182: OptionGroup must track the selected option
 * by its key (the short option, or the long option when no short one exists),
 * not by its short option only.
 */
public class OptionGroupLongOptionRegressionTest extends TestCase
{
    public void testSelectedWithLongOptionOnlyDirect() throws Exception
    {
        OptionGroup group = new OptionGroup();
        Option foo = OptionBuilder.withLongOpt("foo").create();
        Option bar = OptionBuilder.withLongOpt("bar").create();
        group.addOption(foo);
        group.addOption(bar);

        group.setSelected(bar);

        assertEquals("selected option", "bar", group.getSelected());
    }

    public void testOptionGroupLongWithBasicParser() throws Exception
    {
        OptionGroup group = new OptionGroup();
        group.addOption(OptionBuilder.withLongOpt("foo").create());
        group.addOption(OptionBuilder.withLongOpt("bar").create());

        Options options = new Options();
        options.addOptionGroup(group);

        CommandLine cl = new BasicParser().parse(options, new String[] { "--bar" });

        assertTrue(cl.hasOption("bar"));
        assertEquals("selected option", "bar", group.getSelected());
    }

    public void testOptionGroupLongWithGnuParser() throws Exception
    {
        OptionGroup group = new OptionGroup();
        group.addOption(OptionBuilder.withLongOpt("foo").create());
        group.addOption(OptionBuilder.withLongOpt("bar").create());

        Options options = new Options();
        options.addOptionGroup(group);

        CommandLine cl = new GnuParser().parse(options, new String[] { "--bar" });

        assertTrue(cl.hasOption("bar"));
        assertEquals("selected option", "bar", group.getSelected());
    }

    public void testOptionGroupLongWithPosixParser() throws Exception
    {
        OptionGroup group = new OptionGroup();
        group.addOption(OptionBuilder.withLongOpt("foo").create());
        group.addOption(OptionBuilder.withLongOpt("bar").create());

        Options options = new Options();
        options.addOptionGroup(group);

        CommandLine cl = new PosixParser().parse(options, new String[] { "--bar" });

        assertTrue(cl.hasOption("bar"));
        assertEquals("selected option", "bar", group.getSelected());
    }
}