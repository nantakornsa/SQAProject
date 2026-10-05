package org.apache.commons.math.complex;

import org.junit.Assert;
import org.junit.Test;

public class ComplexDivideZeroRegressionTest {

    @Test
    public void testDivideZero() {
        Complex x = new Complex(3.0, 4.0);
        Complex result = x.divide(Complex.ZERO);
        Assert.assertTrue(result.isInfinite());
        Assert.assertFalse(result.isNaN());
        Assert.assertEquals(Complex.INF, result);
    }

    @Test
    public void testDivideZeroDouble() {
        Complex x = new Complex(3.0, 4.0);
        Complex result = x.divide(0.0);
        Assert.assertTrue(result.isInfinite());
        Assert.assertFalse(result.isNaN());
        Assert.assertEquals(Complex.INF, result);
    }

    @Test
    public void testDivideZeroByZeroIsNaN() {
        Assert.assertTrue(Complex.ZERO.divide(Complex.ZERO).isNaN());
        Assert.assertTrue(Complex.ZERO.divide(0.0).isNaN());
    }

    @Test
    public void testAtanI() {
        Assert.assertTrue(Complex.I.atan().isInfinite());
    }
}