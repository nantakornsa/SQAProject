package org.apache.commons.math3.optimization.direct;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-867: the accuracy of the CMAESOptimizer result
 * must not depend on how the bounds are defined.
 */
public class CMAESOptimizerBoundaryRegressionTest {

    @Test
    public void testFitAccuracyDependsOnBoundary() {
        final CMAESOptimizer optimizer = new CMAESOptimizer();
        final MultivariateFunction fitnessFunction = new MultivariateFunction() {
                public double value(double[] parameters) {
                    final double target = 11.1;
                    final double error = target - parameters[0];
                    return error * error;
                }
            };

        final double[] start = { 1 };

        // No bounds.
        PointValuePair result = optimizer.optimize(100000, fitnessFunction, GoalType.MINIMIZE,
                                                   start);
        final double resNoBound = result.getPoint()[0];

        // Optimum is near the lower bound.
        final double[] lower = { -20 };
        final double[] upper = { 5e16 };
        result = optimizer.optimize(100000, fitnessFunction, GoalType.MINIMIZE,
                                    start, lower, upper);
        final double resNearLo = result.getPoint()[0];

        // Optimum is near the upper bound.
        lower[0] = -5e16;
        upper[0] = 20;
        result = optimizer.optimize(100000, fitnessFunction, GoalType.MINIMIZE,
                                    start, lower, upper);
        final double resNearHi = result.getPoint()[0];

        // The results must agree regardless of the bounds definition.
        Assert.assertEquals(resNoBound, resNearLo, 1e-3);
        Assert.assertEquals(resNoBound, resNearHi, 1e-3);
    }
}