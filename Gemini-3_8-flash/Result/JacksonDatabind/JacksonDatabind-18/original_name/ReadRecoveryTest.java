package com.fasterxml.jackson.databind.seq;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadRecoveryTest extends BaseMapTest
{
    static class Bean {
        public int a, b;
    }

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testSimpleRootRecovery() throws Exception
    {
        final String JSON = "{\"a\":3} 123 {\"a\":5}";
        MappingIterator<Bean> it = MAPPER.reader(Bean.class).readValues(JSON);

        assertTrue(it.hasNextValue());
        Bean b1 = it.nextValue();
        assertNotNull(b1);
        assertEquals(3, b1.a);

        try {
            it.nextValue();
            fail("Should have failed on 123");
        } catch (JsonMappingException e) {
            // expected
        }

        assertTrue(it.hasNextValue());
        Bean b2 = it.nextValue();
        assertNotNull(b2);
        assertEquals(5, b2.a);

        assertFalse(it.hasNextValue());
        it.close();
    }
}