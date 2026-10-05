package org.apache.commons.math.optimization.univariate;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.QuinticFunction;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.random.JDKRandomGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-413: the multi-start optimizer must pass the
 * original [min, max] interval and a start value to the underlying optimizer,
 * instead of random sub-intervals.
 */
public class MultiStartUnivariateRealOptimizerRegressionTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testQuinticMinWithMultiStart() throws MathException {
        // The quintic function has zeros at 0, +-0.5 and +-1.
        // The function has extrema (first derivative is zero) at 0.27195613 and 0.82221643,
        UnivariateRealFunction f = new QuinticFunction();
        UnivariateRealOptimizer underlying = new BrentOptimizer(1e-9, 1e-14);
        underlying.setMaxEvaluations(300);
        JDKRandomGenerator g = new JDKRandomGenerator();
        g.setSeed(4312000053L);
        MultiStartUnivariateRealOptimizer optimizer =
            new MultiStartUnivariateRealOptimizer(underlying, 5, g);

        UnivariateRealPointValuePair optimum
            = optimizer.optimize(f, GoalType.MINIMIZE, -0.3, -0.2);
        Assert.assertEquals(-0.2719561293, optimum.getPoint(), 1e-9);
        Assert.assertEquals(-0.0443342695, optimum.getValue(), 1e-9);

        UnivariateRealPointValuePair[] optima = optimizer.getOptima();
        for (int i = 0; i < optima.length; ++i) {
            Assert.assertEquals(f.value(optima[i].getPoint()), optima[i].getValue(), 1e-9);
        }
        Assert.assertTrue(optimizer.getEvaluations() >= 50);
        Assert.assertTrue(optimizer.getEvaluations() <= 100);
    }
}