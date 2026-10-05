package org.apache.commons.math.complex;

import org.junit.Assert;
import org.junit.Test;

public class ComplexAddNaNRegressionTest {

    private final double nan = Double.NaN;

    @Test
    public void testAddNaN() {
        Complex x = new Complex(3.0, 4.0);
        Complex z = x.add(Complex.NaN);
        Assert.assertTrue(z.isNaN());
        z = new Complex(1, nan);
        Complex w = x.add(z);
        Assert.assertTrue(Double.isNaN(w.getReal()));
        Assert.assertTrue(Double.isNaN(w.getImaginary()));
    }

    @Test
    public void testAddToNaNComplex() {
        Complex x = new Complex(1, nan);
        Complex y = new Complex(3.0, 4.0);
        Complex w = x.add(y);
        Assert.assertTrue(Double.isNaN(w.getReal()));
        Assert.assertTrue(Double.isNaN(w.getImaginary()));
        Assert.assertTrue(w.isNaN());
    }
}