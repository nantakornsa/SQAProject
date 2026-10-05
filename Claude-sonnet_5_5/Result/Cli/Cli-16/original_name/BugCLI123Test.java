package org.apache.commons.cli2.bug;

import junit.framework.TestCase;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;

public class BugCLI123Test extends TestCase {

    private Option childOption1;
    private Option childOption2;
    private Group childGroup;
    private Group parentOptions;
    private Parser parser;

    protected void setUp() throws Exception {
        super.setUp();

        final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();

        childOption1 = obuilder
                .withLongName("child")
                .withShortName("c")
                .withArgument(
                        abuilder.withName("value").withMinimum(1)
                                .withMaximum(1).create()).create();

        childOption2 = obuilder
                .withLongName("sub")
                .withShortName("s")
                .withArgument(
                        abuilder.withName("subvalue").withMinimum(1)
                                .withMaximum(1).create()).create();

        childGroup = gbuilder.withName("childGroup")
                .withOption(childOption1).withOption(childOption2)
                .withMinimum(1).withMaximum(2).create();

        parentOptions = gbuilder.withName("parentOptions")
                .withOption(childGroup).withMinimum(1).withMaximum(1)
                .create();

        parser = new Parser();
        parser.setGroup(parentOptions);
    }

    public void testMultipleChildOptions() throws OptionException {
        CommandLine cl = parser.parse(new String[] { "--child", "test",
                "--sub", "anotherTest" });
        assertTrue("Child option not found", cl.hasOption(childOption1));
        assertEquals("Wrong value for option", "test", cl
                .getValue(childOption1));
        assertTrue("Sub option not found", cl.hasOption(childOption2));
        assertEquals("Wrong value for sub option", "anotherTest", cl
                .getValue(childOption2));
        assertTrue("Child group not found", cl.hasOption(childGroup));
    }
}