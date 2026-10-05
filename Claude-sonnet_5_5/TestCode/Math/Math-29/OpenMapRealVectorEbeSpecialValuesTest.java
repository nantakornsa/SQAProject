package org.apache.commons.math3.linear;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-803: element-by-element operations of
 * {@link OpenMapRealVector} must propagate NaN for 0 / 0 and 0 * Infinity
 * (or NaN) at positions where the sparse vector stores a zero.
 */
public class OpenMapRealVectorEbeSpecialValuesTest {

    @Test
    public void testEbeDivideZeroByZeroSameType() {
        final RealVector u = new OpenMapRealVector(new double[] { 0d, 1d, 0d });
        final RealVector v = new OpenMapRealVector(new double[] { 0d, 2d, 4d });
        final RealVector res = u.ebeDivide(v);
        Assert.assertTrue(Double.isNaN(res.getEntry(0)));
        Assert.assertEquals(0.5, res.getEntry(1), 0d);
        Assert.assertEquals(0d, res.getEntry(2), 0d);
    }

    @Test
    public void testEbeDivideZeroByZeroMixedTypes() {
        final RealVector u = new OpenMapRealVector(new double[] { 0d, 1d, 0d });
        final RealVector v = new ArrayRealVector(new double[] { 0d, 2d, 4d });
        final RealVector res = u.ebeDivide(v);
        Assert.assertTrue(Double.isNaN(res.getEntry(0)));
        Assert.assertEquals(0.5, res.getEntry(1), 0d);
        Assert.assertEquals(0d, res.getEntry(2), 0d);
    }

    @Test
    public void testEbeMultiplyZeroTimesInfinitySameType() {
        final RealVector u = new OpenMapRealVector(new double[] { 0d, 1d, 0d });
        final RealVector v = new OpenMapRealVector(new double[] { Double.POSITIVE_INFINITY, 2d, 3d });
        final RealVector res = u.ebeMultiply(v);
        Assert.assertTrue(Double.isNaN(res.getEntry(0)));
        Assert.assertEquals(2d, res.getEntry(1), 0d);
        Assert.assertEquals(0d, res.getEntry(2), 0d);
    }

    @Test
    public void testEbeMultiplyZeroTimesInfinityMixedTypes() {
        final RealVector u = new OpenMapRealVector(new double[] { 0d, 1d, 0d });
        final RealVector v = new ArrayRealVector(new double[] { Double.POSITIVE_INFINITY, 2d, 3d });
        final RealVector res = u.ebeMultiply(v);
        Assert.assertTrue(Double.isNaN(res.getEntry(0)));
        Assert.assertEquals(2d, res.getEntry(1), 0d);
        Assert.assertEquals(0d, res.getEntry(2), 0d);
    }

    @Test
    public void testEbeMultiplyZeroTimesNaN() {
        final RealVector u = new OpenMapRealVector(new double[] { 0d, 1d, 0d });
        final RealVector v = new OpenMapRealVector(new double[] { Double.NaN, 2d, 3d });
        final RealVector res = u.ebeMultiply(v);
        Assert.assertTrue(Double.isNaN(res.getEntry(0)));
        Assert.assertEquals(2d, res.getEntry(1), 0d);
        Assert.assertEquals(0d, res.getEntry(2), 0d);
    }
}