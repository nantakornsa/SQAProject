package org.apache.commons.math3.geometry.euclidean.threed;

import org.junit.Assert;
import org.junit.Test;

public class LineRevertRegressionTest {

    @Test
    public void testRevertDirectionExact() {

        // setup
        Line line = new Line(new Vector3D(1653345.6696423641, 6170370.041579291, 90000),
                             new Vector3D(1650757.5050732433, 6160710.879908984, 0.9));
        Vector3D expected = line.getDirection().negate();

        // action
        Line reverted = line.revert();

        // verify
        Assert.assertArrayEquals(expected.toArray(), reverted.getDirection().toArray(), 0);
    }

    @Test
    public void testRevertTwiceRestoresDirection() {

        Line line = new Line(new Vector3D(1653345.6696423641, 6170370.041579291, 90000),
                             new Vector3D(1650757.5050732433, 6160710.879908984, 0.9));

        Line twice = line.revert().revert();

        Assert.assertArrayEquals(line.getDirection().toArray(), twice.getDirection().toArray(), 0);
    }

    @Test
    public void testRevertKeepsZeroPoint() {

        Line line = new Line(new Vector3D(1653345.6696423641, 6170370.041579291, 90000),
                             new Vector3D(1650757.5050732433, 6160710.879908984, 0.9));

        Line reverted = line.revert();

        Assert.assertArrayEquals(line.getOrigin().toArray(), reverted.getOrigin().toArray(), 1.0e-6);
    }
}