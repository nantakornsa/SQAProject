package org.apache.commons.cli;

import junit.framework.TestCase;

public class TypeHandlerUnsupportedClassTest extends TestCase
{
    public void testCreateValueUnsupportedClassThrowsParseException() throws Exception
    {
        try
        {
            TypeHandler.createValue("5", Integer.class);
            fail("Expected exception: org.apache.commons.cli.ParseException");
        }
        catch (ParseException e)
        {
            // expected
        }
    }

    public void testCreateValueUnsupportedClassMessage() throws Exception
    {
        try
        {
            TypeHandler.createValue("abc", Integer.class);
            fail("Expected exception: org.apache.commons.cli.ParseException");
        }
        catch (ParseException e)
        {
            assertTrue(e.getMessage().startsWith("Unable to handle the class"));
        }
    }
}