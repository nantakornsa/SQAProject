package org.apache.commons.math.util;

import junit.framework.TestCase;

/**
 * Regression test for MATH-370: MathUtils.equals(double, double) must be
 * consistent with equals(double, double, 1), so NaN is never equal to NaN
 * and values exactly one ulp apart are considered equal.
 */
public class MathUtilsEqualsRegressionTest extends TestCase {

    public void testDoubleEqualsNaN() {
        assertFalse(MathUtils.equals(Double.NaN, Double.NaN));
        assertFalse(MathUtils.equals(Double.NaN, 1d));
        assertFalse(MathUtils.equals(1d, Double.NaN));
    }

    public void testDoubleEqualsAdjacentValues() {
        double next = FastMath.nextAfter(1d, 2d);
        assertTrue(MathUtils.equals(1d, next));
        assertTrue(MathUtils.equals(next, 1d));
    }

    public void testDoubleEqualsTwoUlpsApart() {
        double twoUlps = FastMath.nextAfter(FastMath.nextAfter(1d, 2d), 2d);
        assertFalse(MathUtils.equals(1d, twoUlps));
    }

    public void testArrayEqualsWithNaN() {
        assertFalse(MathUtils.equals(new double[] { Double.NaN },
                                     new double[] { Double.NaN }));
        assertTrue(MathUtils.equals(new double[] { 1d, Double.POSITIVE_INFINITY },
                                    new double[] { 1d, Double.POSITIVE_INFINITY }));
        assertFalse(MathUtils.equals(new double[] { 1d },
                                     new double[] { FastMath.nextAfter(FastMath.nextAfter(1d, 2d), 2d) }));
    }
}