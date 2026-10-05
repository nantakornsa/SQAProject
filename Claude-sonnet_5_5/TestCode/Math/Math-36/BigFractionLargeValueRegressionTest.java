package org.apache.commons.math.fraction;

import java.math.BigInteger;

import junit.framework.TestCase;

public class BigFractionLargeValueRegressionTest extends TestCase {

    public void testFloatValueForLargeNumeratorAndDenominator() {
        final BigInteger pow400 = BigInteger.TEN.pow(400);
        final BigInteger pow401 = BigInteger.TEN.pow(401);
        final BigInteger two = new BigInteger("2");
        final BigFraction large = new BigFraction(pow401.add(BigInteger.ONE),
                                                  pow400.multiply(two));

        assertEquals(5.0, large.floatValue(), 1e-15);
    }

    public void testDoubleValueForLargeNumeratorAndDenominator() {
        final BigInteger pow400 = BigInteger.TEN.pow(400);
        final BigInteger pow401 = BigInteger.TEN.pow(401);
        final BigInteger two = new BigInteger("2");
        final BigFraction large = new BigFraction(pow401.add(BigInteger.ONE),
                                                  pow400.multiply(two));

        assertEquals(5.0, large.doubleValue(), 1e-15);
    }
}