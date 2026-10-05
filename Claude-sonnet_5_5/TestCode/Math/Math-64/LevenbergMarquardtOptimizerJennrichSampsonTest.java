package org.apache.commons.math.optimization.general;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.analysis.MultivariateVectorialFunction;
import org.apache.commons.math.optimization.VectorialPointValuePair;

/**
 * Regression test for MATH-405: LevenbergMarquardtOptimizer returned an
 * inaccurate optimum because the residuals were overwritten by Q^T.res and the
 * returned point/value pair was not consistent with the final state.
 *
 * The problem is the Jennrich-Sampson function from the MINPACK test suite.
 */
public class LevenbergMarquardtOptimizerJennrichSampsonTest extends TestCase {

    private static class JennrichSampson
        implements DifferentiableMultivariateVectorialFunction {

        private final int m;

        JennrichSampson(int m) {
            this.m = m;
        }

        public MultivariateVectorialFunction.class_placeholder_unused() {
            return null;
        }
    }
}