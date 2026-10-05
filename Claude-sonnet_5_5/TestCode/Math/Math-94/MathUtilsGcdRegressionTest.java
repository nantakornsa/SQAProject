package org.apache.commons.math.util;

import junit.framework.TestCase;

public class MathUtilsGcdRegressionTest extends TestCase {

    public void testGcdProductOverflow() {
        // u * v overflows to 0 in int arithmetic (27 * 2^35 is a multiple of 2^32)
        assertEquals(3 * (1 << 15), MathUtils.gcd(3 * (1 << 20), 9 * (1 << 15)));
        assertEquals(3 * (1 << 15), MathUtils.gcd(9 * (1 << 15), 3 * (1 << 20)));
        assertEquals(3 * (1 << 15), MathUtils.gcd(-3 * (1 << 20), 9 * (1 << 15)));
        assertEquals(3 * (1 << 15), MathUtils.gcd(3 * (1 << 20), -9 * (1 << 15)));
    }

    public void testGcdPowersOfTwoOverflow() {
        // 65536 * 65536 overflows to 0
        assertEquals(65536, MathUtils.gcd(65536, 65536));
        assertEquals(1 << 16, MathUtils.gcd(1 << 16, 1 << 20));
    }

    public void testGcdWithZero() {
        assertEquals(0, MathUtils.gcd(0, 0));
        assertEquals(50, MathUtils.gcd(0, 50));
        assertEquals(30, MathUtils.gcd(30, 0));
    }
}