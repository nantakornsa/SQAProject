package org.apache.commons.math.stat.inference;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for MATH-175: the chi-square statistic must rescale the
 * expected counts when their sum differs from the sum of the observed counts.
 */
public class ChiSquareRescaleRegressionTest extends TestCase {

    private UnknownDistributionChiSquareTest testStatistic;

    public ChiSquareRescaleRegressionTest(String name) {
        super(name);
    }

    public void setUp() {
        testStatistic = new ChiSquareTestImpl();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ChiSquareRescaleRegressionTest.class);
        suite.setName("ChiSquare rescale regression tests");
        return suite;
    }

    public void testChiSquareRescaledExpected() throws Exception {
        // sum(observed) = 60, sum(expected) = 3 -> expected rescaled to 20 each
        long[] observed = {10, 20, 30};
        double[] expected = {1d, 1d, 1d};
        // (10-20)^2/20 + (20-20)^2/20 + (30-20)^2/20 = 10
        assertEquals("chi-square test statistic", 10.0,
                testStatistic.chiSquare(expected, observed), 1E-10);
    }

    public void testChiSquareRescaledProportionalExpected() throws Exception {
        // expected is exactly proportional to observed -> statistic is zero
        long[] observed = {20, 40, 60};
        double[] expected = {10d, 20d, 30d};
        assertEquals("chi-square test statistic", 0.0,
                testStatistic.chiSquare(expected, observed), 1E-10);
    }
}