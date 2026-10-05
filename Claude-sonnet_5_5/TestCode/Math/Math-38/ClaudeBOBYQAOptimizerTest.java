package org.apache.commons.math.optimization.direct;

import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-728: BOBYQAOptimizer threw PathIsExploredException
 * (and used wrong indices) in "prelim" when more interpolation points than
 * the minimum (2 * dim + 1) were used.
 */
public class ClaudeBOBYQAOptimizerTest {

    private static final int DIM = 13;

    @Test
    public void testConstrainedRosenWithMoreInterpolationPoints() {
        final double[] startPoint = point(DIM, 0.1);
        final double[][] boundaries = boundaries(DIM, -1, 2);
        final RealPointValuePair expected = new RealPointValuePair(point(DIM, 1.0), 0.0);

        final int maxAdditionalPoints = 47;

        for (int num = 1; num <= maxAdditionalPoints; num++) {
            doTest(new Rosen(), startPoint, boundaries,
                   GoalType.MINIMIZE,
                   1e-12, 1e-6, 2000,
                   num,
                   expected,
                   "num=" + num);
        }
    }

    private void doTest(MultivariateRealFunction func,
                        double[] startPoint,
                        double[][] boundaries,
                        GoalType goal,
                        double fTol,
                        double pointTol,
                        int maxEvaluations,
                        int additionalInterpolationPoints,
                        RealPointValuePair expected,
                        String assertMsg) {
        final int dim = startPoint.length;
        final int numIterpolationPoints = 2 * dim + 1 + additionalInterpolationPoints;
        final BOBYQAOptimizer optim = new BOBYQAOptimizer(numIterpolationPoints);
        final RealPointValuePair result = optim.optimize(maxEvaluations, func, goal,
                                                         startPoint,
                                                         boundaries[0], boundaries[1]);
        final double[] resNum = result.getPoint();
        final double[] expNum = expected.getPoint();
        for (int i = 0; i < dim; i++) {
            Assert.assertEquals(assertMsg, expNum[i], resNum[i], pointTol);
        }
        Assert.assertEquals(assertMsg, expected.getValue(), result.getValue(), fTol);
    }

    private static double[] point(int n, double value) {
        double[] ds = new double[n];
        java.util.Arrays.fill(ds, value);
        return ds;
    }

    private static double[][] boundaries(int dim, double lower, double upper) {
        double[][] boundaries = new double[2][dim];
        for (int i = 0; i < dim; i++) {
            boundaries[0][i] = lower;
        }
        for (int i = 0; i < dim; i++) {
            boundaries[1][i] = upper;
        }
        return boundaries;
    }

    private static class Rosen implements MultivariateRealFunction {
        public double value(double[] x) {
            double f = 0;
            for (int i = 0; i < x.length - 1; ++i) {
                double a = x[i] * x[i] - x[i + 1];
                double b = 1.0 - x[i];
                f += 100 * a * a + b * b;
            }
            return f;
        }
    }
}