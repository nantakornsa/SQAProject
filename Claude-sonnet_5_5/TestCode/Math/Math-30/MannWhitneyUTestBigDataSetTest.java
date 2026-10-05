package org.apache.commons.math3.stat.inference;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-790: integer overflow in the normal approximation
 * of the Mann-Whitney U test when the sample sizes are large.
 */
public class MannWhitneyUTestBigDataSetTest {

    private final MannWhitneyUTest testStatistic = new MannWhitneyUTest();

    @Test
    public void testBigDataSet() throws Exception {
        double[] d1 = new double[1500];
        double[] d2 = new double[1500];
        for (int i = 0; i < 1500; i++) {
            d1[i] = 2 * i;
            d2[i] = 2 * i + 1;
        }
        double result = testStatistic.mannWhitneyUTest(d1, d2);
        Assert.assertTrue(result > 0.1);
    }
}