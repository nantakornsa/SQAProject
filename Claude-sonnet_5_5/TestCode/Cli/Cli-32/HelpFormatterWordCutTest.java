package org.apache.commons.cli;

import junit.framework.TestCase;

/**
 * Regression test for CLI-193: HelpFormatter throws
 * StringIndexOutOfBoundsException when a word has to be cut at the wrap width.
 */
public class HelpFormatterWordCutTest extends TestCase
{
    private static final String EOL = System.getProperty("line.separator");

    public void testRenderWrappedTextWordCut()
    {
        int width = 7;
        int padding = 0;
        String text = "Thisisatest.";
        String expected = "Thisisa" + EOL +
                          "test.";

        StringBuffer sb = new StringBuffer();
        new HelpFormatter().renderWrappedText(sb, width, padding, text);
        assertEquals("cut and wrap", expected, sb.toString());
    }

    public void testFindWrapPosWordCut()
    {
        HelpFormatter formatter = new HelpFormatter();

        // no whitespace within the first 3 characters: must cut exactly at width
        String text = "aaaa aa";
        assertEquals("wrap position", 3, formatter.findWrapPos(text, 3, 0));
    }
}