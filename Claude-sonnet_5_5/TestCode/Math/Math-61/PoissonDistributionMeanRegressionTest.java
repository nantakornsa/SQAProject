package org.apache.commons.math.distribution;

import junit.framework.TestCase;

import org.apache.commons.math.exception.NotStrictlyPositiveException;

/**
 * Regression test for MATH-349: the Poisson distribution constructor must
 * throw NotStrictlyPositiveException for a non-positive mean.
 */
public class PoissonDistributionMeanRegressionTest extends TestCase {

    public void testMean() {
        PoissonDistribution dist;
        try {
            dist = new PoissonDistributionImpl(-1);
            fail("negative mean: NotStrictlyPositiveException expected");
        } catch (NotStrictlyPositiveException ex) {
            // Expected.
        }

        dist = new PoissonDistributionImpl(10.0);
        assertEquals(10.0, dist.getMean(), 0.0);
    }

    public void testZeroMean() {
        try {
            new PoissonDistributionImpl(0.0);
            fail("zero mean: NotStrictlyPositiveException expected");
        } catch (NotStrictlyPositiveException ex) {
            // Expected.
        }
    }
}