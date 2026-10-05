package org.apache.commons.math.util;

import org.junit.Assert;
import org.junit.Test;

public class FastMathMaxFloatRegressionTest {

    @Test
    public void testMaxFloatFirstArgumentLarger() {
        Assert.assertEquals("max(50.0, -50.0)", 50.0f,
                            FastMath.max(50.0f, -50.0f), MathUtils.EPSILON);
        Assert.assertEquals("max(-50.0, 50.0)", 50.0f,
                            FastMath.max(-50.0f, 50.0f), MathUtils.EPSILON);
        Assert.assertEquals("max(3.0, 1.0)", 3.0f,
                            FastMath.max(3.0f, 1.0f), MathUtils.EPSILON);
        Assert.assertEquals("max(+inf, 1.0)", Float.POSITIVE_INFINITY,
                            FastMath.max(Float.POSITIVE_INFINITY, 1.0f), MathUtils.EPSILON);
        Assert.assertEquals("max(1.0, -inf)", 1.0f,
                            FastMath.max(1.0f, Float.NEGATIVE_INFINITY), MathUtils.EPSILON);
        Assert.assertTrue("max(NaN, 1.0)", Float.isNaN(FastMath.max(Float.NaN, 1.0f)));
        Assert.assertTrue("max(1.0, NaN)", Float.isNaN(FastMath.max(1.0f, Float.NaN)));
    }
}