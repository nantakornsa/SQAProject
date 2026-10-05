package org.apache.commons.cli;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;

public class PatternOptionBuilderExistingFileRegressionTest extends TestCase
{
    public void testExistingFilePatternReturnsFileInputStream() throws Exception
    {
        File tmp = File.createTempFile("cli274", ".file");
        tmp.deleteOnExit();

        Options options = PatternOptionBuilder.parsePattern("g<");
        CommandLineParser parser = new PosixParser();
        CommandLine line = parser.parse(options, new String[] { "-g", tmp.getAbsolutePath() });

        Object parsed = line.getParsedOptionValue("g");
        try
        {
            assertTrue("option g not FileInputStream", parsed instanceof FileInputStream);
        }
        finally
        {
            if (parsed instanceof FileInputStream)
            {
                ((FileInputStream) parsed).close();
            }
        }
    }

    public void testTypeHandlerCreateValueForExistingFile() throws Exception
    {
        File tmp = File.createTempFile("cli274", ".file");
        tmp.deleteOnExit();

        Object value = TypeHandler.createValue(tmp.getAbsolutePath(), PatternOptionBuilder.EXISTING_FILE_VALUE);
        try
        {
            assertTrue("value not FileInputStream", value instanceof FileInputStream);
        }
        finally
        {
            if (value instanceof FileInputStream)
            {
                ((FileInputStream) value).close();
            }
        }
    }
}