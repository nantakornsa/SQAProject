package org.apache.commons.math.estimation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for MATH-200: AbstractEstimator used all parameters
 * (including bound ones) instead of only the unbound ones when computing
 * covariances and parameter errors.
 */
public class AbstractEstimatorBoundParametersTest extends TestCase {

    public AbstractEstimatorBoundParametersTest(String name) {
        super(name);
    }

    public void testBoundParameters() throws EstimationException {
        EstimatedParameter[] p = {
            new EstimatedParameter("unbound0", 2, false),
            new EstimatedParameter("unbound1", 2, false),
            new EstimatedParameter("bound",    2, true)
        };
        LinearProblem problem = new LinearProblem(p, new LinearMeasurement[] {
            new LinearMeasurement(new double[] { 1.0, 1.0, 1.0 },
                                  new EstimatedParameter[] { p[0], p[1], p[2] },
                                  3.0),
            new LinearMeasurement(new double[] { 1.0, -1.0, 1.0 },
                                  new EstimatedParameter[] { p[0], p[1], p[2] },
                                  1.0),
            new LinearMeasurement(new double[] { 1.0, 3.0, 2.0 },
                                  new EstimatedParameter[] { p[0], p[1], p[2] },
                                  7.0)
        });

        GaussNewtonEstimator estimator = new GaussNewtonEstimator(100, 1.0e-6, 1.0e-6);
        estimator.estimate(problem);
        assertTrue(estimator.getRMS(problem) < 1.0e-10);
        double[][] covariances = estimator.getCovariances(problem);
        int i0 = 0, i1 = 1;
        if (problem.getUnboundParameters()[0].getName().endsWith("1")) {
            i0 = 1;
            i1 = 0;
        }
        assertEquals(11.0 / 24, covariances[i0][i0], 1.0e-10);
        assertEquals(-3.0 / 24, covariances[i0][i1], 1.0e-10);
        assertEquals(-3.0 / 24, covariances[i1][i0], 1.0e-10);
        assertEquals( 3.0 / 24, covariances[i1][i1], 1.0e-10);

        double[] errors = estimator.guessParametersErrors(problem);
        assertEquals(2, errors.length);
        assertEquals(0, errors[i0], 1.0e-10);
        assertEquals(0, errors[i1], 1.0e-10);
    }

    public static Test suite() {
        return new TestSuite(AbstractEstimatorBoundParametersTest.class);
    }

    private static class LinearProblem implements EstimationProblem {

        private final EstimatedParameter[] allParameters;
        private final WeightedMeasurement[] measurements;

        public LinearProblem(EstimatedParameter[] parameters,
                             LinearMeasurement[] measurements) {
            this.allParameters = parameters.clone();
            this.measurements = new WeightedMeasurement[measurements.length];
            for (int i = 0; i < measurements.length; ++i) {
                this.measurements[i] = measurements[i];
            }
        }

        public WeightedMeasurement[] getMeasurements() {
            return measurements;
        }

        public EstimatedParameter[] getAllParameters() {
            return allParameters;
        }

        public EstimatedParameter[] getUnboundParameters() {
            int count = 0;
            for (int i = 0; i < allParameters.length; ++i) {
                if (!allParameters[i].isBound()) {
                    ++count;
                }
            }
            EstimatedParameter[] unbound = new EstimatedParameter[count];
            int index = 0;
            for (int i = 0; i < allParameters.length; ++i) {
                if (!allParameters[i].isBound()) {
                    unbound[index++] = allParameters[i];
                }
            }
            return unbound;
        }
    }

    private static class LinearMeasurement extends WeightedMeasurement {

        private static final long serialVersionUID = 1L;

        private final double[] factors;
        private final EstimatedParameter[] parameters;

        public LinearMeasurement(double[] factors,
                                 EstimatedParameter[] parameters,
                                 double setPoint) {
            super(1.0, setPoint);
            this.factors    = factors.clone();
            this.parameters = parameters.clone();
        }

        public double getTheoreticalValue() {
            double v = 0;
            for (int i = 0; i < factors.length; ++i) {
                v += factors[i] * parameters[i].getEstimate();
            }
            return v;
        }

        public double getPartial(EstimatedParameter parameter) {
            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] == parameter) {
                    return factors[i];
                }
            }
            return 0;
        }
    }
}