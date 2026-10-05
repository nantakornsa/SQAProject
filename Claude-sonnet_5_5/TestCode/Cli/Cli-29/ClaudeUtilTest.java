package org.apache.commons.cli;

import junit.framework.TestCase;

public class ClaudeUtilTest extends TestCase
{
    public void testStripLeadingHyphens()
    {
        assertEquals("f", Util.stripLeadingHyphens("-f"));
        assertEquals("foo", Util.stripLeadingHyphens("--foo"));
        assertEquals("-foo", Util.stripLeadingHyphens("---foo"));
        assertNull(Util.stripLeadingHyphens(null));
    }

    public void testStripLeadingAndTrailingQuotes()
    {
        assertEquals("foo", Util.stripLeadingAndTrailingQuotes("\"foo\""));
        assertEquals("foo \"bar\"", Util.stripLeadingAndTrailingQuotes("foo \"bar\""));
        assertEquals("\"foo\" bar", Util.stripLeadingAndTrailingQuotes("\"foo\" bar"));
        assertEquals("\"foo\" \"bar\"", Util.stripLeadingAndTrailingQuotes("\"foo\" \"bar\""));
        assertEquals("\"", Util.stripLeadingAndTrailingQuotes("\""));
    }
}