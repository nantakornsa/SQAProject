package org.apache.commons.math3.optimization.univariate;

import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.function.StepFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.junit.Assert;
import org.junit.Test;

public class BrentOptimizerMath855Test {

    @Test
    public void testMath855() {
        final double minSin = 3 * Math.PI / 2;
        final double offset = 1e-8;
        final double delta = 1e-7;
        final UnivariateFunction f1 = new Sin();
        final UnivariateFunction f2 = new StepFunction(new double[] { minSin, minSin + offset, minSin + 5 * offset },
                                                       new double[] { 0, -1, 0 });
        final UnivariateFunction f = FunctionUtils.add(f1, f2);
        final UnivariateOptimizer optimizer = new BrentOptimizer(1e-8, 1e-100);
        final UnivariatePointValuePair result
            = optimizer.optimize(200, f, GoalType.MINIMIZE, minSin - 6.789 * delta, minSin + 9.876 * delta);

        final double sol = result.getPoint();
        final double expected = 4.712389027602411;

        Assert.assertTrue("Best point not reported", f.value(sol) <= f.value(expected));
    }
}