package org.apache.commons.math.analysis.solvers;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-631: the Regula Falsi solver failed to converge
 * because the update formula could not make any progress.
 */
public class RegulaFalsiSolverTest {

    @Test
    public void testIssue631() {
        final UnivariateRealFunction f = new UnivariateRealFunction() {
            public double value(double x) {
                return Math.exp(x) - Math.pow(Math.PI, 3.0);
            }
        };

        final UnivariateRealSolver solver = new RegulaFalsiSolver();
        final double root = solver.solve(3624, f, 1, 10);
        Assert.assertEquals(3.4341896575482003, root, 1e-15);
    }
}