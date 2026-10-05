package org.apache.commons.math.util;

import junit.framework.TestCase;

public class MathUtilsDistanceOverflowTest extends TestCase {

    public void testDistanceIntOverflow() {
        int[] p1 = new int[] { 0 };
        int[] p2 = new int[] { 50000 };
        assertEquals(50000.0, MathUtils.distance(p1, p2), 1.0e-9);
    }

    public void testDistanceIntLargeDifferences() {
        int[] p1 = new int[] { 1959, 325100 };
        int[] p2 = new int[] { 1960, 373200 };
        double expected = Math.sqrt(1.0 + 48100.0 * 48100.0);
        assertEquals(expected, MathUtils.distance(p1, p2), 1.0e-6);
    }
}