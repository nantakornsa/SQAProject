package org.apache.commons.collections.map;

import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-261 in Flat3Map.
 */
public class Flat3MapCollections261Test extends TestCase {

    public Flat3MapCollections261Test(String testName) {
        super(testName);
    }

    public void testCollections261() {
        Flat3Map m = new Flat3Map();
        m.put(new Integer(1), new Integer(1));
        m.put(new Integer(0), new Integer(0));
        assertEquals(new Integer(1), m.remove(new Integer(1)));
        assertEquals(new Integer(0), m.remove(new Integer(0)));

        m.put(new Integer(2), new Integer(2));
        m.put(new Integer(1), new Integer(1));
        m.put(new Integer(0), new Integer(0));
        assertEquals(new Integer(2), m.remove(new Integer(2)));
        assertEquals(new Integer(1), m.remove(new Integer(1)));
        assertEquals(new Integer(0), m.remove(new Integer(0)));
    }
}