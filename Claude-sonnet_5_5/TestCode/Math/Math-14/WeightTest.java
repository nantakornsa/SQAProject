package org.apache.commons.math3.optim.nonlinear.vector;

import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class WeightTest {

    @Test
    public void testDiagonalWeightIsStoredAsDiagonalMatrix() {
        final double[] w = { 1, 2, 3, 4 };
        final RealMatrix m = new Weight(w).getWeight();

        Assert.assertTrue(m instanceof DiagonalMatrix);
        Assert.assertEquals(w.length, m.getRowDimension());
        Assert.assertEquals(w.length, m.getColumnDimension());
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w.length; j++) {
                Assert.assertEquals(i == j ? w[i] : 0d, m.getEntry(i, j), 0d);
            }
        }
    }

    @Test
    public void testLargeDiagonalWeight() {
        // A dense matrix of this size would exhaust the heap (MATH-924).
        final int n = 40000;
        final double[] w = new double[n];
        for (int i = 0; i < n; i++) {
            w[i] = 1.0;
        }
        final RealMatrix m = new Weight(w).getWeight();

        Assert.assertTrue(m instanceof DiagonalMatrix);
        Assert.assertEquals(n, m.getRowDimension());
        Assert.assertEquals(1.0, m.getEntry(n - 1, n - 1), 0d);
        Assert.assertEquals(0.0, m.getEntry(0, n - 1), 0d);
    }
}