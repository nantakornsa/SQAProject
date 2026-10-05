package org.apache.commons.lang3.time;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Regression test for LANG-818: FastDatePrinter should respect the Calendar's
 * timezone when formatting timezone names ('z' or 'zzzz').
 */
public class FastDatePrinterLang818Test {

    @Test
    public void testCalendarTimezoneRespected() {
        String pattern = "h:mma z";
        TimeZone printerZone = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone calZone = TimeZone.getTimeZone("UTC");

        FastDateFormat format = FastDateFormat.getInstance(pattern, printerZone, Locale.US);

        Calendar cal = Calendar.getInstance(calZone, Locale.US);
        cal.clear();
        cal.set(2012, Calendar.JANUARY, 1, 14, 43, 0);

        String expected = "2:43PM UTC";
        String actual = format.format(cal);

        assertEquals(expected, actual);
    }
}