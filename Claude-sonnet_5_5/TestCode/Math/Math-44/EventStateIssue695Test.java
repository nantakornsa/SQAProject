package org.apache.commons.math.ode.events;

import org.apache.commons.math.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math.ode.nonstiff.DormandPrince853Integrator;
import org.junit.Assert;
import org.junit.Test;

public class EventStateIssue695Test {

    @Test
    public void testIssue695() {

        ResettingEvent.lastTriggerTime = Double.NEGATIVE_INFINITY;

        FirstOrderDifferentialEquations equation = new FirstOrderDifferentialEquations() {

            public int getDimension() {
                return 1;
            }

            public void computeDerivatives(double t, double[] y, double[] yDot) {
                yDot[0] = 1.0;
            }
        };

        DormandPrince853Integrator integrator = new DormandPrince853Integrator(0.001, 1000, 1.0e-14, 1.0e-14);
        integrator.addEventHandler(new ResettingEvent(10.99), 0.1, 1.0e-9, 1000);
        integrator.addEventHandler(new ResettingEvent(11.01), 0.1, 1.0e-9, 1000);
        integrator.setInitialStepSize(3.0);

        double target = 30.0;
        double[] y = new double[1];
        double tEnd = integrator.integrate(equation, 0.0, y, target, y);
        Assert.assertEquals(target, tEnd, 1.0e-10);
        Assert.assertEquals(32.0, y[0], 1.0e-10);
    }

    private static class ResettingEvent implements EventHandler {

        private static double lastTriggerTime = Double.NEGATIVE_INFINITY;

        private final double tEvent;

        public ResettingEvent(final double tEvent) {
            this.tEvent = tEvent;
        }

        public double g(double t, double[] y) {
            return t - tEvent;
        }

        public int eventOccurred(double t, double[] y, boolean increasing) {
            // the event times must be monotonic
            Assert.assertTrue("going backard in time! (" + t + " < " + lastTriggerTime + ")",
                              t >= lastTriggerTime);
            lastTriggerTime = t;
            return RESET_STATE;
        }

        public void resetState(double t, double[] y) {
            y[0] += 1.0;
        }
    }
}