package org.apache.commons.math3.distribution;

import org.junit.Assert;
import org.junit.Test;

public class MultivariateNormalDistributionDensityRegressionTest {

    @Test
    public void testUnivariateDensityAtMean() {
        final double[] mu = { 0 };
        final double[][] sigma = { { 1 } };
        final MultivariateNormalDistribution multi = new MultivariateNormalDistribution(mu, sigma);

        final double expected = 1.0 / Math.sqrt(2 * Math.PI);
        Assert.assertEquals(expected, multi.density(new double[] { 0 }), 1e-12);
    }

    @Test
    public void testUnivariateMatchesNormalDistribution() {
        final double[] mu = { -1.5 };
        final double[][] sigma = { { 1 } };
        final MultivariateNormalDistribution multi = new MultivariateNormalDistribution(mu, sigma);
        final NormalDistribution uni = new NormalDistribution(mu[0], sigma[0][0]);

        final double[] points = { -5, -3.2, -1.5, 0, 1.7, 4.9 };
        for (double v : points) {
            Assert.assertEquals(uni.density(v), multi.density(new double[] { v }), 1e-12);
        }
    }

    @Test
    public void testThreeDimensionalDensityAtMean() {
        final double[] mu = { 0, 0, 0 };
        final double[][] sigma = {
            { 1, 0, 0 },
            { 0, 1, 0 },
            { 0, 0, 1 }
        };
        final MultivariateNormalDistribution multi = new MultivariateNormalDistribution(mu, sigma);

        final double expected = Math.pow(2 * Math.PI, -1.5);
        Assert.assertEquals(expected, multi.density(new double[] { 0, 0, 0 }), 1e-12);
    }
}