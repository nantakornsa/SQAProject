package org.apache.commons.math.complex;

import org.junit.Assert;
import org.junit.Test;

public class ComplexDivideByZeroRegressionTest {

    @Test
    public void testAtanI() {
        Assert.assertTrue(Complex.I.atan().isNaN());
    }

    @Test
    public void testDivideComplexByZero() {
        Complex numerator = new Complex(1.0, 1.0);
        Complex result = numerator.divide(Complex.ZERO);
        Assert.assertTrue(result.isNaN());
    }

    @Test
    public void testDivideDoubleByZero() {
        Complex numerator = new Complex(1.0, 1.0);
        Complex result = numerator.divide(0.0);
        Assert.assertTrue(result.isNaN());
    }

    @Test
    public void testDivideZeroByZero() {
        Assert.assertTrue(Complex.ZERO.divide(Complex.ZERO).isNaN());
        Assert.assertTrue(Complex.ZERO.divide(0.0).isNaN());
    }
}