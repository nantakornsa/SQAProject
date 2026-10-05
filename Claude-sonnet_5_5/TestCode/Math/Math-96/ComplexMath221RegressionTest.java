package org.apache.commons.math.complex;

import junit.framework.TestCase;

public class ComplexMath221RegressionTest extends TestCase {

    public void testMath221() {
        assertEquals(new Complex(0, -1), new Complex(0, 1).multiply(new Complex(-1, 0)));
    }

    public void testMath221SignedZeroEquals() {
        assertTrue(new Complex(0.0, 1.0).equals(new Complex(-0.0, 1.0)));
        assertTrue(new Complex(1.0, 0.0).equals(new Complex(1.0, -0.0)));
    }
}