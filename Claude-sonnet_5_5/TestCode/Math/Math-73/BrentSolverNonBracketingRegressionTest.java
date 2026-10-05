package org.apache.commons.math.analysis.solvers;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.SinFunction;
import org.apache.commons.math.analysis.UnivariateRealFunction;

/**
 * Regression test for MATH-343: BrentSolver must reject non-bracketing
 * intervals when an initial guess is supplied.
 */
public class BrentSolverNonBracketingRegressionTest extends TestCase {

    public void testBadEndpointsWithInitialGuess() throws Exception {
        UnivariateRealFunction f = new SinFunction();
        UnivariateRealSolver solver = new BrentSolver();
        try {  // no bracket, initial guess supplied
            solver.solve(f, 1, 1.5, 1.2);
            fail("Expecting IllegalArgumentException - non-bracketing");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    public void testBadEndpointsWithoutInitialGuess() throws Exception {
        UnivariateRealFunction f = new SinFunction();
        UnivariateRealSolver solver = new BrentSolver();
        try {  // no bracket
            solver.solve(f, 1, 1.5);
            fail("Expecting IllegalArgumentException - non-bracketing");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}