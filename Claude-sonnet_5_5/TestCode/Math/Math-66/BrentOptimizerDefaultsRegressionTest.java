package org.apache.commons.math.optimization.univariate;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.UnivariateRealOptimizer;

import junit.framework.TestCase;

/**
 * Regression test for MATH-395 (BrentOptimizer default settings and
 * integration with the abstract optimizer framework).
 */
public class BrentOptimizerDefaultsRegressionTest extends TestCase {

    private static class SineFunction implements UnivariateRealFunction {
        public double value(double x) throws FunctionEvaluationException {
            return Math.sin(x);
        }
    }

    public void testDefaultAccuracies() {
        UnivariateRealOptimizer optimizer = new BrentOptimizer();
        assertEquals(1e-11, optimizer.getAbsoluteAccuracy(), 1e-20);
        assertEquals(1e-9, optimizer.getRelativeAccuracy(), 1e-18);
        assertEquals(100, optimizer.getMaximalIterationCount());
    }

    public void testSinMinWithDefaults() throws MathException {
        UnivariateRealFunction f = new SineFunction();
        UnivariateRealOptimizer optimizer = new BrentOptimizer();
        double result = optimizer.optimize(f, GoalType.MINIMIZE, 4, 5);
        assertEquals(3 * Math.PI / 2, result, 1e-8);
        assertEquals(result, optimizer.getResult(), 1e-20);
        assertEquals(-1.0, optimizer.getFunctionValue(), 1e-12);
    }
}