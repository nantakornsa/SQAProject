package org.apache.commons.math.dfp;

import org.apache.commons.math.util.FastMath;
import org.apache.commons.math.util.MathUtils;
import org.junit.Assert;
import org.junit.Test;

public class DfpZeroSignRegressionTest {

    @Test
    public void testIssue567() {
        DfpField field = new DfpField(100);
        Assert.assertEquals(0.0, field.getZero().toDouble(), MathUtils.SAFE_MIN);
        Assert.assertEquals(0.0, field.newDfp(0.0).toDouble(), MathUtils.SAFE_MIN);
        Assert.assertEquals(-1, FastMath.copySign(1, field.newDfp(-0.0).toDouble()), MathUtils.EPSILON);
        Assert.assertEquals(+1, FastMath.copySign(1, field.newDfp(+0.0).toDouble()), MathUtils.EPSILON);
    }
}