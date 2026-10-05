package org.joda.time.field;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for FieldUtils.safeMultiply(long, int) overflow handling
 * when multiplying Long.MIN_VALUE by -1.
 */
public class TestFieldUtilsSafeMultiplyOverflow extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestFieldUtilsSafeMultiplyOverflow.class);
    }

    public TestFieldUtilsSafeMultiplyOverflow(String name) {
        super(name);
    }

    public void testSafeMultiplyLongMinValueByMinusOneOverflows() {
        try {
            FieldUtils.safeMultiply(Long.MIN_VALUE, -1);
            fail("Expected ArithmeticException for Long.MIN_VALUE * -1");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    public void testSafeMultiplyLongMaxValueByMinusOneDoesNotOverflow() {
        assertEquals(-Long.MAX_VALUE, FieldUtils.safeMultiply(Long.MAX_VALUE, -1));
    }

    public void testSafeMultiplyLongMinValueByOne() {
        assertEquals(Long.MIN_VALUE, FieldUtils.safeMultiply(Long.MIN_VALUE, 1));
    }
}