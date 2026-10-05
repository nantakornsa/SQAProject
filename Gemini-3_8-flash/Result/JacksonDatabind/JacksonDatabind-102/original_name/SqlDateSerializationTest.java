package com.fasterxml.jackson.databind.ser.jdk;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SqlDateSerializationTest extends BaseMapTest
{
    public void testSqlDateConfigOverride() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configOverride(Date.class)
            .setFormat(JsonFormat.Value.forPattern("yyyy+MM+dd"));

        Date date = new Date(0L);
        // Using a fixed millisecond value for reproducibility (e.g. 1980-04-14 UTC or timezone-sensitive)
        // With pattern "yyyy+MM+dd", the output must be formatted as a string rather than a timestamp number
        String json = mapper.writeValueAsString(date);
        assertTrue("Expected string formatted date with '+' but got: " + json,
                json.startsWith("\"") && json.endsWith("\"") && json.contains("+"));
    }
}