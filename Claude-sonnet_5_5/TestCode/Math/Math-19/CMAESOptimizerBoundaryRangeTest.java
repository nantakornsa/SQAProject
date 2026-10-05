package org.apache.commons.math3.optimization.direct;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.util.FastMath;
import org.junit.Assert;
import org.junit.Test;

public class CMAESOptimizerBoundaryRangeTest {

    @Test
    public void testBoundaryRangeTooLarge() {
        final CMAESOptimizer optimizer = new CMAESOptimizer();
        final MultivariateFunction fitnessFunction = new MultivariateFunction() {
                public double value(double[] parameters) {
                    if (Double.isNaN(parameters[0])) {
                        throw new MathIllegalStateException();
                    }
                    final double target = 1;
                    final double error = target - parameters[0];
                    return error * error;
                }
            };

        final double[] start = { 0 };

        // The difference between upper and lower bounds is used to
        // normalize the variables: in case of overflow, NaN is produced.
        final double max = Double.MAX_VALUE / 2;
        final double tooLarge = FastMath.nextUp(max);
        final double[] lower = { -tooLarge };
        final double[] upper = { tooLarge };

        try {
            optimizer.optimize(10000, fitnessFunction, GoalType.MINIMIZE,
                               start, lower, upper);
            Assert.fail("NumberIsTooLargeException expected");
        } catch (NumberIsTooLargeException e) {
            // expected
        }
    }
}