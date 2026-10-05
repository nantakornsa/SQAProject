package com.fasterxml.jackson.databind.ser;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateFormatTimeZoneTest extends BaseMapTest
{
    // [databind#889]
    public void testDateFormatConfigDoesNotOverwriteTimeZone()
    {
        ObjectMapper mapper = new ObjectMapper();
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        mapper.setTimeZone(tz);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        mapper.setDateFormat(df);

        assertEquals(tz, mapper.getSerializationConfig().getTimeZone());
        assertEquals(tz, mapper.getDeserializationConfig().getTimeZone());
    }
}