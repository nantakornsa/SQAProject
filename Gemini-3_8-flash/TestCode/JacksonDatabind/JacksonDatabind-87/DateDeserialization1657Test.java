package com.fasterxml.jackson.databind.deser;

import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateDeserialization1657Test extends BaseMapTest {

    public void testDateUtilISO8601NoTimezoneNonDefault() throws Exception {
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(tz);

        String dateStr = "1970-01-01T00:00:00.000";
        Date date = mapper.readValue(quote(dateStr), Date.class);

        // In GMT+2, 1970-01-01T00:00:00 is 2 hours before UTC epoch: -7200000L
        assertEquals(-7200000L, date.getTime());
    }
}