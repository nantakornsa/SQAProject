package org.apache.commons.math.stat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for MATH-259: Frequency.addValue(Object) with a
 * non-comparable value should throw ClassCastException.
 */
public final class FrequencyTest extends TestCase {

    private Frequency f = null;

    public FrequencyTest(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        f = new Frequency();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(FrequencyTest.class);
        suite.setName("Frequency Tests");
        return suite;
    }

    public void testAddNonComparable() {
        try {
            f.addValue(new Object());
            fail("Expected ClassCastException");
        } catch (ClassCastException expected) {
            // expected
        }
        f.clear();
        f.addValue(1);
        try {
            f.addValue(new Object());
            fail("Expected ClassCastException");
        } catch (ClassCastException expected) {
            // expected
        }
    }
}