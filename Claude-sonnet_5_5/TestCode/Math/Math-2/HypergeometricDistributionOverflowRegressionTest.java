package org.apache.commons.math3.distribution;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-1021: integer overflow in
 * HypergeometricDistribution#getNumericalMean.
 */
public class HypergeometricDistributionOverflowRegressionTest {

    @Test
    public void testMath1021Mean() {
        final int N = 43130568;
        final int m = 42976365;
        final int n = 50;
        final HypergeometricDistribution dist = new HypergeometricDistribution(N, m, n);

        final double expected = n * ((double) m / (double) N);
        final double mean = dist.getNumericalMean();

        Assert.assertTrue("mean=" + mean, mean > 0);
        Assert.assertEquals(expected, mean, 1e-9);
    }

    @Test
    public void testMath1021Sample() {
        final int N = 43130568;
        final int m = 42976365;
        final int n = 50;
        final HypergeometricDistribution dist = new HypergeometricDistribution(N, m, n);

        for (int i = 0; i < 100; i++) {
            final int sample = dist.sample();
            Assert.assertTrue("sample=" + sample, 0 <= sample);
            Assert.assertTrue("sample=" + sample, sample <= n);
        }
    }
}