package org.apache.commons.collections.map;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;

public class MultiValueMapTest extends TestCase {

    public MultiValueMapTest(String testName) {
        super(testName);
    }

    public void testPutReturnsValueWhenKeyIsNew() {
        MultiValueMap map = MultiValueMap.decorate(new HashMap(), ArrayList.class);
        Object result = map.put("key1", "value1");
        assertEquals("put should return the inserted value when key is not present", "value1", result);
    }

    public void testPutAllReturnsTrueWhenKeyIsNew() {
        MultiValueMap map = MultiValueMap.decorate(new HashMap(), ArrayList.class);
        ArrayList values = new ArrayList();
        values.add("value1");
        values.add("value2");
        boolean result = map.putAll("key1", values);
        assertTrue("putAll should return true when inserting into a new key", result);
    }
}