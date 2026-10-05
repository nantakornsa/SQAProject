package org.apache.commons.math.stat;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * Regression test for MATH-259: Frequency.addValue(Object) must throw
 * IllegalArgumentException (not ClassCastException) for non-Comparable values.
 */
public final class ClaudeFrequencyTest extends TestCase {

    private Frequency f;

    public ClaudeFrequencyTest(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        f = new Frequency();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ClaudeFrequencyTest.class);
        suite.setName("Frequency Tests");
        return suite;
    }

    @SuppressWarnings("deprecation")
    public void testAddNonComparable() {
        try {
            f.addValue(new Object());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        f.clear();
        f.addValue(1);
        try {
            f.addValue(new Object());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}