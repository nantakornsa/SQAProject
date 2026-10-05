package org.apache.commons.cli;

import junit.framework.TestCase;

public class OptionBuilderResetRegressionTest extends TestCase
{
    public void testBuilderIsResettedAfterFailedCreate()
    {
        try
        {
            OptionBuilder.withDescription("JUnit").create('"');
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }

        // the builder must have been reset even though create() failed
        assertNull("we inherited a description", OptionBuilder.create('x').getDescription());
    }
}