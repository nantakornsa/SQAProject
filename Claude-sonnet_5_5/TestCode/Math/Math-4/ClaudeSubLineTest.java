package org.apache.commons.math3.geometry.euclidean.threed;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class ClaudeSubLineTest {

    @Test
    public void testIntersectionNotIntersecting() throws MathIllegalArgumentException {
        SubLine sub1 = new SubLine(new Vector3D(1, 1, 1), new Vector3D(1.5, 1, 1));
        SubLine sub2 = new SubLine(new Vector3D(2, 3, 0), new Vector3D(2, 3, 0.5));
        Assert.assertNull(sub1.intersection(sub2, true));
        Assert.assertNull(sub1.intersection(sub2, false));
    }
}