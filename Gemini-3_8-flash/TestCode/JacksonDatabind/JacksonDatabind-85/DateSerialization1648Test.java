package com.fasterxml.jackson.databind.ser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateSerialization1648Test extends BaseMapTest {

    static class DateWrapper {
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        public Date date;

        public DateWrapper(Date date) {
            this.date = date;
        }
    }

    public void testFormatWithoutPattern() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'X'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        mapper.setDateFormat(df);

        DateWrapper input = new DateWrapper(new Date(0L));
        String json = mapper.writeValueAsString(input);
        assertEquals("{\"date\":\"1970-01-01X01:00:00\"}", json);
    }
}