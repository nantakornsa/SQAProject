package org.apache.commons.math3.fraction;

import org.apache.commons.math3.exception.ConvergenceException;
import org.junit.Assert;
import org.junit.Test;

public class DigitLimitConstructorRegressionTest {

    @Test
    public void testFractionDigitLimitConstructorMath996() throws ConvergenceException {
        Fraction f = new Fraction(0.5000000001, 10);
        Assert.assertEquals(1, f.getNumerator());
        Assert.assertEquals(2, f.getDenominator());
    }

    @Test
    public void testBigFractionDigitLimitConstructorMath996() throws ConvergenceException {
        BigFraction f = new BigFraction(0.5000000001, 10);
        Assert.assertEquals(1, f.getNumeratorAsInt());
        Assert.assertEquals(2, f.getDenominatorAsInt());
    }
}