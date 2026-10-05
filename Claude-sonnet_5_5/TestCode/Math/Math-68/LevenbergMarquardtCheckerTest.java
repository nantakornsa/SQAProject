package org.apache.commons.math.optimization.general;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.optimization.VectorialConvergenceChecker;
import org.apache.commons.math.optimization.VectorialPointValuePair;

/**
 * Regression test for MATH-362: the Levenberg-Marquardt optimizer
 * ignored a user-supplied convergence checker.
 */
public class LevenbergMarquardtCheckerTest extends TestCase {

    private static class CountingChecker implements VectorialConvergenceChecker {
        int calls = 0;

        public boolean converged(int iteration,
                                 VectorialPointValuePair previous,
                                 VectorialPointValuePair current) {
            ++calls;
            // always request termination
            return true;
        }
    }

    public void testConvergenceCheckerIsUsed() throws Exception {
        DifferentiableMultivariateVectorialFunction rosenbrock =
            new DifferentiableMultivariateVectorialFunction() {
                public double[] value(double[] x) {
                    return new double[] {
                        10.0 * (x[1] - x[0] * x[0]),
                        1.0 - x[0]
                    };
                }

                public MultivariateMatrixFunction jacobian() {
                    return new MultivariateMatrixFunction() {
                        public double[][] value(double[] x) {
                            return new double[][] {
                                { -20.0 * x[0], 10.0 },
                                { -1.0, 0.0 }
                            };
                        }
                    };
                }
            };

        LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();
        CountingChecker checker = new CountingChecker();
        optimizer.setConvergenceChecker(checker);

        optimizer.optimize(rosenbrock,
                           new double[] { 0.0, 0.0 },
                           new double[] { 1.0, 1.0 },
                           new double[] { -1.2, 1.0 });

        assertTrue("convergence checker was never called", checker.calls > 0);
        assertEquals(1, checker.calls);
        assertEquals(1, optimizer.getIterations());
    }
}