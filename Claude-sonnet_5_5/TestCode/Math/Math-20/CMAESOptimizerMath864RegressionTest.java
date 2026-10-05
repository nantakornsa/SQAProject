package org.apache.commons.math3.optimization.direct;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.junit.Assert;
import org.junit.Test;

public class CMAESOptimizerMath864RegressionTest {

    @Test
    public void testMath864() {
        final CMAESOptimizer optimizer = new CMAESOptimizer();
        final MultivariateFunction fitnessFunction = new MultivariateFunction() {
                public double value(double[] parameters) {
                    final double target = 1;
                    final double error = target - parameters[0];
                    return error * error;
                }
            };

        final double[] start = { 0 };
        final double[] lower = { -1e6 };
        final double[] upper = { 0.5 };
        final double[] result = optimizer.optimize(10000, fitnessFunction, GoalType.MINIMIZE,
                                                   start, lower, upper).getPoint();
        Assert.assertTrue("Out of bounds (" + result[0] + " > " + upper[0] + ")",
                          result[0] <= upper[0]);
    }
}