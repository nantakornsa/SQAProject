package org.apache.commons.math.linear;

import junit.framework.TestCase;

public class LInfNormRegressionTest extends TestCase {

    private final double[] vec = { -4d, 0d, 3d, 1d, -6d, 3d };

    public void testArrayRealVectorLInfNorm() {
        ArrayRealVector v = new ArrayRealVector(vec);
        assertEquals("compare values  ", 6.0, v.getLInfNorm(), 1.0e-12);
    }

    public void testArrayRealVectorLInfNormAllNegative() {
        ArrayRealVector v = new ArrayRealVector(new double[] { -1d, -2d, -3d });
        assertEquals("compare values  ", 3.0, v.getLInfNorm(), 1.0e-12);
    }

    public void testOpenMapRealVectorLInfNorm() {
        OpenMapRealVector v = new OpenMapRealVector(vec);
        assertEquals("compare values  ", 6.0, v.getLInfNorm(), 1.0e-12);
    }

    public void testOpenMapRealVectorLInfNormAllNegative() {
        OpenMapRealVector v = new OpenMapRealVector(new double[] { -1d, -2d, -3d });
        assertEquals("compare values  ", 3.0, v.getLInfNorm(), 1.0e-12);
    }
}