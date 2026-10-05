package com.fasterxml.jackson.databind.ser.jdk;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

public class StdDateFormatYearFormattingTest extends BaseMapTest
{
    private final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new StdDateFormat().withTimeZone(TimeZone.getTimeZone("UTC")).withLocale(Locale.US));

    public void testDateISO8601_10k() throws Exception
    {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        c.set(Calendar.ERA, GregorianCalendar.AD);
        c.set(Calendar.YEAR, 10204);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        String json = MAPPER.writeValueAsString(c.getTime());
        assertEquals("\"+10204-01-01T00:00:00.000+0000\"", json);
    }

    public void testDateISO8601_BCE() throws Exception
    {
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        c.set(Calendar.ERA, GregorianCalendar.BC);
        c.set(Calendar.YEAR, 1);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        String json = MAPPER.writeValueAsString(c.getTime());
        assertEquals("\"+0000-01-01T00:00:00.000+0000\"", json);
    }
}