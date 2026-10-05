package org.apache.commons.math3.util;

import org.junit.Assert;
import org.junit.Test;

public class FastMathMath905RegressionTest {

    @Test
    public void testMath905LargePositive() {
        final double start = StrictMath.log(Double.MAX_VALUE);
        final double endT = StrictMath.sqrt(2) * StrictMath.sqrt(Double.MAX_VALUE);
        final double end = 2 * StrictMath.log(endT);

        double maxErr = 0;
        for (double x = start; x < end; x += 1e-3) {
            final double tst = FastMath.cosh(x);
            final double ref = Math.cosh(x);
            if (Double.isInfinite(ref)) {
                continue;
            }
            maxErr = FastMath.max(maxErr, FastMath.abs(ref - tst) / FastMath.ulp(ref));
        }
        Assert.assertEquals(0, maxErr, 3);

        maxErr = 0;
        for (double x = start; x < end; x += 1e-3) {
            final double tst = FastMath.sinh(x);
            final double ref = Math.sinh(x);
            if (Double.isInfinite(ref)) {
                continue;
            }
            maxErr = FastMath.max(maxErr, FastMath.abs(ref - tst) / FastMath.ulp(ref));
        }
        Assert.assertEquals(0, maxErr, 3);
    }

    @Test
    public void testMath905LargeNegative() {
        final double start = -StrictMath.log(Double.MAX_VALUE);
        final double endT = StrictMath.sqrt(2) * StrictMath.sqrt(Double.MAX_VALUE);
        final double end = -2 * StrictMath.log(endT);

        double maxErr = 0;
        for (double x = start; x > end; x -= 1e-3) {
            final double tst = FastMath.cosh(x);
            final double ref = Math.cosh(x);
            if (Double.isInfinite(ref)) {
                continue;
            }
            maxErr = FastMath.max(maxErr, FastMath.abs(ref - tst) / FastMath.ulp(ref));
        }
        Assert.assertEquals(0, maxErr, 3);

        maxErr = 0;
        for (double x = start; x > end; x -= 1e-3) {
            final double tst = FastMath.sinh(x);
            final double ref = Math.sinh(x);
            if (Double.isInfinite(ref)) {
                continue;
            }
            maxErr = FastMath.max(maxErr, FastMath.abs(ref - tst) / FastMath.ulp(ref));
        }
        Assert.assertEquals(0, maxErr, 3);
    }
}