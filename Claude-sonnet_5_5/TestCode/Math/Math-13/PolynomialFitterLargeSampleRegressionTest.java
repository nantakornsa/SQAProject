package org.apache.commons.math3.optimization.fitting;

import java.util.Random;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.optimization.general.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-924: using a diagonal weight matrix with a large
 * number of observations must not exhaust memory (the square root of a
 * diagonal weight matrix must be computed without an eigen decomposition).
 */
public class PolynomialFitterLargeSampleRegressionTest {

    @Test
    public void testLargeSampleDiagonalWeights() {
        final Random randomizer = new Random(0x5551480dca5b369bL);
        final double[] coeff = { 0.5, -1.0, 2.0 };
        final PolynomialFunction p = new PolynomialFunction(coeff);

        final PolynomialFitter fitter = new PolynomialFitter(new LevenbergMarquardtOptimizer());
        for (int i = 0; i < 40000; ++i) {
            final double x = -1.0 + i / 20000.0;
            fitter.addObservedPoint(1.0, x, p.value(x) + 0.1 * randomizer.nextGaussian());
        }

        final double[] init = new double[coeff.length];
        final PolynomialFunction fitted = new PolynomialFunction(fitter.fit(init));

        for (double x = -1.0; x < 1.0; x += 0.01) {
            final double error = FastMath.abs(p.value(x) - fitted.value(x)) /
                                 (1.0 + FastMath.abs(p.value(x)));
            Assert.assertTrue(error < 0.01);
        }

        final double[] fittedCoeff = fitted.getCoefficients();
        Assert.assertEquals(coeff.length, fittedCoeff.length);
        for (int i = 0; i < coeff.length; ++i) {
            Assert.assertEquals(coeff[i], fittedCoeff[i], 0.05);
        }
    }
}