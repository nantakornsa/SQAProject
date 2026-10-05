package org.apache.commons.cli2.bug;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.option.DefaultOption;
import org.apache.commons.cli2.validation.FileValidator;

public class BugCLI144Test extends TestCase {

    public void testFileValidator() {
        final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();
        DefaultOption fileNameOption = obuilder.withShortName("f")
                .withLongName("file-name").withRequired(true).withDescription(
                        "name of an existing file").withArgument(
                        abuilder.withName("file-name").withValidator(
                                FileValidator.getExistingFileInstance())
                                .create()).create();
        Group options = gbuilder.withName("options").withOption(fileNameOption)
                .create();
        Parser parser = new Parser();
        parser.setHelpTrigger("--help");
        parser.setGroup(options);

        final String fileName = "src/test/org/apache/commons/cli2/bug/BugCLI144Test.java";
        CommandLine cl = parser
                .parseAndHelp(new String[] { "--file-name", fileName });
        assertNotNull(cl);
        assertEquals("Wrong file", new File(fileName), cl.getValue(fileNameOption));
    }
}