package org.jfree.data.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.data.KeyedObjects2D;
import org.jfree.data.UnknownKeyException;

/**
 * Tests for the {@link KeyedObjects2D} class.
 */
public class GeminiKeyedObjects2DTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiKeyedObjects2DTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiKeyedObjects2DTests(String name) {
        super(name);
    }

    /**
     * A test for removeColumn(Comparable) when not all rows contain
     * an entry for the column to be removed.
     */
    public void testRemoveColumnByKey() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeColumn("C2");
        assertEquals(1, data.getColumnCount());
        assertEquals("Obj1", data.getObject(0, 0));

        // try unknown column key
        boolean pass = false;
        try {
            data.removeColumn("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // try null column key
        pass = false;
        try {
            data.removeColumn(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }
}