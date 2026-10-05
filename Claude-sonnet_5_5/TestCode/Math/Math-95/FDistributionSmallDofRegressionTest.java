package org.apache.commons.math.distribution;

import junit.framework.TestCase;

public class FDistributionSmallDofRegressionTest extends TestCase {

    public FDistributionSmallDofRegressionTest(String name) {
        super(name);
    }

    public void testSmallDegreesOfFreedom() throws Exception {
        FDistributionImpl fd = new FDistributionImpl(1.0, 1.0);
        double p = fd.cumulativeProbability(0.975);
        double x = fd.inverseCumulativeProbability(p);
        assertEquals(0.975, x, 1.0e-5);

        fd.setDenominatorDegreesOfFreedom(2.0);
        p = fd.cumulativeProbability(0.975);
        x = fd.inverseCumulativeProbability(p);
        assertEquals(0.975, x, 1.0e-5);
    }

    public void testInitialDomainDenominatorBelowTwo() throws Exception {
        FDistributionImpl fd = new FDistributionImpl(2.0, 0.5);
        double p = fd.cumulativeProbability(1.5);
        double x = fd.inverseCumulativeProbability(p);
        assertEquals(1.5, x, 1.0e-3);
    }
}