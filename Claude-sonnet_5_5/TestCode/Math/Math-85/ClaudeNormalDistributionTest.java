package org.apache.commons.math.distribution;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils;

import junit.framework.TestCase;

/**
 * Regression test for MATH-280.
 */
public class ClaudeNormalDistributionTest extends TestCase {

    public void testMath280() throws MathException {
        NormalDistribution normal = new NormalDistributionImpl(0, 1);
        double result = normal.inverseCumulativeProbability(0.9772498680518209);
        assertEquals(2.0, result, 1.0e-12);
    }

    public void testBracketEndpointIsRoot() throws MathException {
        UnivariateRealFunction f = new UnivariateRealFunction() {
            public double value(double x) throws MathException {
                return x - 2.0;
            }
        };
        double[] bracket = UnivariateRealSolverUtils.bracket(f, 1.0, 0.0, 10.0);
        assertNotNull(bracket);
        assertEquals(2, bracket.length);
        assertTrue(bracket[0] <= 2.0);
        assertTrue(bracket[1] >= 2.0);
    }
}