package org.apache.commons.math.stat.descriptive;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.math.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.Variance;

/**
 * Regression test for MATH-691: overriding statistics with the default
 * math classes must still produce correct results.
 */
public class SummaryStatisticsOverrideRegressionTest extends TestCase {

    private static final double[] SCORES = {1, 2, 3, 4};

    public void testOverrideMeanWithMathClass() throws Exception {
        SummaryStatistics stats = new SummaryStatistics();
        stats.setMeanImpl(new Mean());
        for (double i : SCORES) {
            stats.addValue(i);
        }
        Assert.assertEquals((new Mean()).evaluate(SCORES), stats.getMean(), 0);
    }

    public void testOverrideGeoMeanWithMathClass() throws Exception {
        SummaryStatistics stats = new SummaryStatistics();
        stats.setGeoMeanImpl(new GeometricMean());
        for (double i : SCORES) {
            stats.addValue(i);
        }
        Assert.assertEquals((new GeometricMean()).evaluate(SCORES),
                stats.getGeometricMean(), 0);
    }

    public void testOverrideVarianceWithMathClass() throws Exception {
        SummaryStatistics stats = new SummaryStatistics();
        stats.setVarianceImpl(new Variance());
        for (double i : SCORES) {
            stats.addValue(i);
        }
        Assert.assertEquals((new Variance()).evaluate(SCORES),
                stats.getVariance(), 0);
    }
}