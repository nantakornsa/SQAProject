package org.apache.commons.cli;

import junit.framework.TestCase;

/**
 * Regression test for CLI-133: Util.stripLeadingHyphens must handle null input.
 */
public class UtilTest extends TestCase
{
    public void testStripLeadingHyphens()
    {
        assertEquals("f", Util.stripLeadingHyphens("-f"));
        assertEquals("foo", Util.stripLeadingHyphens("--foo"));
        assertEquals("foo", Util.stripLeadingHyphens("foo"));
        assertNull(Util.stripLeadingHyphens(null));
    }
}