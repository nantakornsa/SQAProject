package org.apache.commons.math.geometry;

import junit.framework.TestCase;

import org.apache.commons.math.util.FastMath;

public class Vector3DCrossProductCancellationTest extends TestCase {

    private static void checkVector(Vector3D v, double x, double y, double z) {
        assertEquals(x, v.getX(), 1.0e-12);
        assertEquals(y, v.getY(), 1.0e-12);
        assertEquals(z, v.getZ(), 1.0e-12);
    }

    public void testCrossProductCancellation() {
        Vector3D v1 = new Vector3D(9070467121.0, 4535233560.0, 1);
        Vector3D v2 = new Vector3D(9070467123.0, 4535233561.0, 1);
        checkVector(Vector3D.crossProduct(v1, v2), -1, 2, 1);

        double scale    = FastMath.scalb(1.0, 100);
        Vector3D big1   = new Vector3D(scale, v1);
        Vector3D small2 = new Vector3D(1 / scale, v2);
        checkVector(Vector3D.crossProduct(big1, small2), -1, 2, 1);
    }

}