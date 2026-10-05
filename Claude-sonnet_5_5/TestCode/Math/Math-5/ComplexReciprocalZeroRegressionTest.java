package org.apache.commons.math3.complex;

import org.junit.Assert;
import org.junit.Test;

public class ComplexReciprocalZeroRegressionTest {

    @Test
    public void testReciprocalZero() {
        Complex result = Complex.ZERO.reciprocal();
        Assert.assertEquals(Complex.INF, result);
        Assert.assertTrue(Double.isInfinite(result.getReal()));
        Assert.assertTrue(Double.isInfinite(result.getImaginary()));
        Assert.assertFalse(result.isNaN());
    }
}