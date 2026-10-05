package org.apache.commons.math.ode.nonstiff;

import junit.framework.TestCase;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.IntegratorException;
import org.apache.commons.math.ode.TestProblem6;
import org.apache.commons.math.ode.TestProblemHandler;

/**
 * Regression test for MATH-338: the initial step size computed by the
 * embedded Runge-Kutta starter integrator must take the relative tolerance
 * into account.
 */
public class AdamsMoultonStarterStepSizeTest extends TestCase {

    public void testPolynomialEvaluationCounts()
        throws DerivativeException, IntegratorException {

        TestProblem6 pb = new TestProblem6();
        double range = Math.abs(pb.getFinalTime() - pb.getInitialTime());

        for (int nSteps = 1; nSteps < 7; ++nSteps) {
            AdamsMoultonIntegrator integ =
                new AdamsMoultonIntegrator(nSteps, 1.0e-6 * range, 0.1 * range, 1.0e-9, 1.0e-9);
            TestProblemHandler handler = new TestProblemHandler(pb, integ);
            integ.addStepHandler(handler);
            integ.integrate(pb, pb.getInitialTime(), pb.getInitialState(),
                            pb.getFinalTime(), new double[pb.getDimension()]);
            if (nSteps < 4) {
                assertTrue("nSteps=" + nSteps + ", evaluations=" + integ.getEvaluations(),
                           integ.getEvaluations() > 140);
            } else {
                assertTrue("nSteps=" + nSteps + ", evaluations=" + integ.getEvaluations(),
                           integ.getEvaluations() < 90);
            }
        }
    }

}