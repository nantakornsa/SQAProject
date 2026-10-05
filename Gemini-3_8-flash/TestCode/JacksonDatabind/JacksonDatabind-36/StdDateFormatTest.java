package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.BaseMapTest;

import java.text.ParseException;

public class StdDateFormatTest extends BaseMapTest
{
    public void testLenient() throws Exception
    {
        StdDateFormat df = new StdDateFormat();
        // Should not throw NullPointerException
        df.setLenient(false);
        assertFalse(df.isLenient());

        // Also test parsing with non-lenient mode rejects invalid date
        try {
            df.parse("2015-11-32");
            fail("Should have failed to parse invalid date with lenient=false");
        } catch (ParseException e) {
            // expected
        }

        df.setLenient(true);
        assertTrue(df.isLenient());
    }
}