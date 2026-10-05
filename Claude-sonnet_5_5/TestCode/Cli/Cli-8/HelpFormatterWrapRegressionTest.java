package org.apache.commons.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import junit.framework.TestCase;

/**
 * Regression test for CLI-151: wrapped continuation lines were computed
 * with the padding offset, so padded lines could exceed the width.
 */
public class HelpFormatterWrapRegressionTest extends TestCase
{
    public void testPrintWrappedPaddedContinuationLines()
    {
        int width = 20;
        int padding = 4;
        String text = "aaaa bbbb cccc dddd eeee ffff gggg hhhh";
        String expected = "aaaa bbbb cccc dddd" + HelpFormatter.DEFAULT_NEW_LINE_PLACEHOLDER_FIX
                + "    eeee ffff gggg" + HelpFormatter.DEFAULT_NEW_LINE_PLACEHOLDER_FIX
                + "    hhhh";

        StringBuffer sb = new StringBuffer();
        new HelpFormatter().renderWrappedText(sb, width, padding, text);
        assertEquals("padded continuation lines must be wrapped at the width", expected, sb.toString());
    }
}