package org.apache.commons.math.optimization;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.QuinticFunction;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.random.JDKRandomGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test: getResult() and getFunctionValue() of the multi-start
 * optimizer must return the best optimum found, not the last one.
 */
public class MultiStartUnivariateRealOptimizerRegressionTest extends TestCase {

    public MultiStartUnivariateRealOptimizerRegressionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(MultiStartUnivariateRealOptimizerRegressionTest.class);
    }

    public void testBestResultAndValueAreReturned() throws MathException {
        UnivariateRealFunction f = new QuinticFunction();
        UnivariateRealOptimizer underlying = new BrentOptimizer();
        JDKRandomGenerator g = new JDKRandomGenerator();
        g.setSeed(4312000053l);
        MultiStartUnivariateRealOptimizer minimizer =
            new MultiStartUnivariateRealOptimizer(underlying, 5, g);
        minimizer.setAbsoluteAccuracy(10 * minimizer.getAbsoluteAccuracy());
        minimizer.setRelativeAccuracy(10 * minimizer.getRelativeAccuracy());

        double returned = minimizer.optimize(f, GoalType.MINIMIZE, -0.3, -0.2);

        double[] optima = minimizer.getOptima();
        double[] optimaValues = minimizer.getOptimaValues();

        assertEquals(-0.27195612846834, returned, 1.0e-13);
        assertEquals(returned, minimizer.getResult(), 0.0);
        assertEquals(optima[0], minimizer.getResult(), 0.0);
        assertEquals(-0.27195612846834, minimizer.getResult(), 1.0e-13);

        assertEquals(optimaValues[0], minimizer.getFunctionValue(), 0.0);
        assertEquals(-0.04433426954946, minimizer.getFunctionValue(), 1.0e-13);
    }
}