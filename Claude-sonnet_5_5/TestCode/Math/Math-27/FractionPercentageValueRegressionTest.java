package org.apache.commons.math3.fraction;

import org.junit.Assert;
import org.junit.Test;

public class FractionPercentageValueRegressionTest {

    @Test
    public void testMath835() {
        final int numer = Integer.MAX_VALUE / 99;
        final int denom = 1;
        final double percentage = 100 * ((double) numer) / denom;
        final Fraction frac = new Fraction(numer, denom);
        // With the buggy implementation, multiply(100) overflows the int numerator.
        Assert.assertEquals(percentage, frac.percentageValue(), Math.ulp(percentage));
    }
}