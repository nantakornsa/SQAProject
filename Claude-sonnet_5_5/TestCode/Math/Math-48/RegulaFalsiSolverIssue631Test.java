package org.apache.commons.math.analysis.solvers;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.exception.ConvergenceException;
import org.junit.Test;

/**
 * Regression test for MATH-631: the Regula Falsi solver must detect early
 * that it is stuck and report a {@link ConvergenceException}, instead of
 * exhausting the evaluation budget with a TooManyEvaluationsException.
 */
public class RegulaFalsiSolverIssue631Test {

    @Test(expected = ConvergenceException.class)
    public void testIssue631() {
        final UnivariateRealFunction f = new UnivariateRealFunction() {
            /** {@inheritDoc} */
            public double value(double x) {
                return Math.exp(x) - Math.pow(Math.PI, 3.0);
            }
        };

        final UnivariateRealSolver solver = new RegulaFalsiSolver();
        solver.solve(3624, f, 1, 10);
    }
}