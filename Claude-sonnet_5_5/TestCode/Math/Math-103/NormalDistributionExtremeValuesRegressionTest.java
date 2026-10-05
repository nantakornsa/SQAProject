package org.apache.commons.math.distribution;

import junit.framework.TestCase;

/**
 * Regression test for MATH-167: NormalDistributionImpl.cumulativeProbability
 * must not throw a MaxIterationsExceededException for extreme values.
 */
public class NormalDistributionExtremeValuesRegressionTest extends TestCase {

    public NormalDistributionExtremeValuesRegressionTest(String name) {
        super(name);
    }

    public void testExtremeValues() throws Exception {
        NormalDistribution distribution = new NormalDistributionImpl();
        distribution.setMean(0);
        distribution.setStandardDeviation(1);
        for (int i = 0; i < 100; i += 5) { // make sure no convergence exception
            double lowerTail = distribution.cumulativeProbability((double) -i);
            double upperTail = distribution.cumulativeProbability((double) i);
            if (i < 10) { // make sure not top-coded
                assertTrue(lowerTail > 0.0d);
                assertTrue(upperTail < 1.0d);
            } else { // make sure top coding not reversed
                assertTrue(lowerTail < 0.00001);
                assertTrue(upperTail > 0.99999);
            }
        }
    }

    public void testExtremeValuesNonStandardParameters() throws Exception {
        NormalDistribution distribution = new NormalDistributionImpl();
        distribution.setMean(5);
        distribution.setStandardDeviation(2);
        assertEquals(0.0d, distribution.cumulativeProbability(5 - 2 * 60), 1e-10);
        assertEquals(1.0d, distribution.cumulativeProbability(5 + 2 * 60), 1e-10);
    }
}