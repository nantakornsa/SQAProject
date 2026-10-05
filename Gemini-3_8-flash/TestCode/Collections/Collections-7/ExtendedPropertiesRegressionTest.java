package org.apache.commons.collections;

import java.util.Iterator;
import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-278.
 */
public class ExtendedPropertiesRegressionTest extends TestCase {

    public ExtendedPropertiesRegressionTest(String testName) {
        super(testName);
    }

    public void testPutAndRemoveUpdatesKeys() {
        ExtendedProperties props = new ExtendedProperties();
        props.put("key1", "value1");
        props.put("key2", "value2");
        props.put("key3", "value3");

        Iterator it = props.getKeys();
        assertTrue("Iterator should have next element", it.hasNext());
        assertEquals("key1", it.next());
        assertTrue("Iterator should have next element", it.hasNext());
        assertEquals("key2", it.next());
        assertTrue("Iterator should have next element", it.hasNext());
        assertEquals("key3", it.next());
        assertFalse("Iterator should have no more elements", it.hasNext());

        props.remove("key2");

        it = props.getKeys();
        assertTrue("Iterator should have next element", it.hasNext());
        assertEquals("key1", it.next());
        assertTrue("Iterator should have next element", it.hasNext());
        assertEquals("key3", it.next());
        assertFalse("Iterator should have no more elements", it.hasNext());
    }
}