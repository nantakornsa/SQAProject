package org.apache.commons.math.analysis.solvers;

import org.apache.commons.math.analysis.SinFunction;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.analysis.QuinticFunction;
import junit.framework.TestCase;

/**
 * Regression test for MATH-369: BisectionSolver.solve(f, min, max, initial)
 * ignored the supplied function and threw a NullPointerException.
 */
public class BisectionSolverTest extends TestCase {

    public void testMath369() throws Exception {
        UnivariateRealFunction f = new SinFunction();
        UnivariateRealSolver solver = new BisectionSolver();
        assertEquals(Math.PI, solver.solve(f, 3.0, 3.2, 3.1), solver.getAbsoluteAccuracy());
    }
}