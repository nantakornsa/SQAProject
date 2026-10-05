package com.mchange.v2.c3p0;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

public class ComboPooledDataSourceTest extends TestCase {

    static class DummyDataSource {
        public DummyDataSource() { }
    }

    public void testC3P0SubTypeBlocked() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();

        // Target class begins with "com.mchange.v2.c3p0." and ends with "DataSource"
        String clsName = DummyDataSource.class.getName();
        String json = "[\"" + clsName + "\", {}]";

        try {
            mapper.readValue(json, Object.class);
            fail("Should not allow deserialization of " + clsName);
        } catch (JsonMappingException e) {
            assertTrue("Expected exception to contain 'Illegal type', got: " + e.getMessage(),
                    e.getMessage().contains("Illegal type"));
        }
    }
}