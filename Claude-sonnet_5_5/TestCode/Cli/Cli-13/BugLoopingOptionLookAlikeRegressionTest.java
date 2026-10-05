package org.apache.commons.cli2.bug;

import junit.framework.TestCase;

import org.apache.commons.cli2.Argument;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.option.SourceDestArgument;

public class BugLoopingOptionLookAlikeRegressionTest extends TestCase {

    public void testLoopingOptionLookAlikeTooManyValues() {
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();
        final Argument inputfile_opt = abuilder.withName("input").withMinimum(1).withMaximum(1).create();
        final Argument outputfile_opt = abuilder.withName("output").withMinimum(1).withMaximum(1).create();
        final Argument targets = new SourceDestArgument(inputfile_opt, outputfile_opt);
        final Group options = gbuilder.withOption(targets).create();
        final Parser parser = new Parser();
        parser.setGroup(options);
        try {
            parser.parse(new String[] { "testfile.txt", "testfile.txt", "testfile.txt", "testfile.txt" });
            fail("OptionException");
        } catch (OptionException e) {
            assertEquals("Unexpected testfile.txt while processing ", e.getMessage());
        }
    }
}