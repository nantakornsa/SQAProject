package org.apache.commons.collections4.map;

import java.util.HashMap;
import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-474.
 */
public class ListOrderedMapRegressionTest extends TestCase {

    public void testCOLLECTIONS_474_nullValues() {
        Object key1 = new Object();
        Object key2 = new Object();
        HashMap<Object, Object> hmap = new HashMap<Object, Object>();
        hmap.put(key1, null);
        hmap.put(key2, null);
        assertEquals("Should have two elements", 2, hmap.size());

        ListOrderedMap<Object, Object> listMap = new ListOrderedMap<Object, Object>();
        listMap.put(key1, null);
        listMap.put(key2, null);
        assertEquals("Should have two elements", 2, listMap.size());

        // In the buggy version, since old value was null, index is wrongly incremented
        // causing IndexOutOfBoundsException when putting the second entry.
        listMap.putAll(2, hmap);
        assertEquals(2, listMap.size());
    }
}