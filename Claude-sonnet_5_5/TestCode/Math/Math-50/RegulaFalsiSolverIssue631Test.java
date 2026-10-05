package org.apache.commons.math.analysis.solvers;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.exception.TooManyEvaluationsException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-631: the Regula Falsi solver must not apply the
 * extra bracket-shrinking step, which hid the slow convergence of the method.
 * With the fix, the solver exceeds the evaluation budget and throws.
 */
public class RegulaFalsiSolverIssue631Test {

    @Test
    public void testIssue631() {
        final UnivariateRealFunction f = new UnivariateRealFunction() {
            /** {@inheritDoc} */
            public double value(double x) {
                return Math.exp(x) - Math.pow(Math.PI, 3.0);
            }
        };

        final UnivariateRealSolver solver = new RegulaFalsiSolver();
        try {
            solver.solve(3624, f, 1, 10);
            Assert.fail("Expected TooManyEvaluationsException");
        } catch (TooManyEvaluationsException expected) {
            // Expected on the fixed version.
        }
    }
}