package org.apache.commons.math.linear;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * Regression test for MATH-320: wrong singular value decomposition
 * (U matrix) for rank-deficient or non-full-rank matrices.
 */
public class SingularValueDecompositionMath320Test extends TestCase {

    public void testMath320A() {
        RealMatrix rm = new Array2DRowRealMatrix(new double[][] {
            { 1.0, 2.0, 3.0 }, { 2.0, 3.0, 4.0 }, { 3.0, 5.0, 7.0 }
        });
        double s439  = Math.sqrt(439.0);
        double[] reference = new double[] {
            Math.sqrt(3.0 * (21.0 + s439)), Math.sqrt(3.0 * (21.0 - s439))
        };
        SingularValueDecomposition svd =
            new SingularValueDecompositionImpl(rm);

        // check we get the expected theoretical singular values
        double[] singularValues = svd.getSingularValues();
        Assert.assertEquals(reference.length, singularValues.length);
        for (int i = 0; i < reference.length; ++i) {
            Assert.assertEquals(reference[i], singularValues[i], 4.0e-13);
        }

        // check the decomposition allows to recover the original matrix
        RealMatrix recomposed = svd.getU().multiply(svd.getS()).multiply(svd.getVT());
        Assert.assertEquals(0.0, recomposed.subtract(rm).getNorm(), 5.0e-13);

        // check we can solve a singular system
        double[] b = new double[] { 5.0, 6.0, 7.0 };
        double[] resSVD = svd.getSolver().solve(b);
        Assert.assertEquals(rm.getColumnDimension(), resSVD.length);

        // check the solution really minimizes the residuals
        double svdMinResidual = residual(rm, resSVD, b);
        double epsilon = 2 * Math.ulp(svdMinResidual);
        double h = 0.1;
        int    k = 3;
        for (double d0 = -k * h; d0 <= k * h; d0 += h) {
            for (double d1 = -k * h ; d1 <= k * h; d1 += h) {
                for (double d2 = -k * h; d2 <= k * h; d2 += h) {
                    double[] x = new double[] { resSVD[0] + d0, resSVD[1] + d1, resSVD[2] + d2 };
                    Assert.assertTrue((residual(rm, x, b) - svdMinResidual) > -epsilon);
                }
            }
        }
    }

    public void testMath320B() {
        RealMatrix rm = new Array2DRowRealMatrix(new double[][] {
            { 1.0, 2.0 }, { 1.0, 2.0 }
        });
        SingularValueDecomposition svd =
            new SingularValueDecompositionImpl(rm);
        RealMatrix recomposed = svd.getU().multiply(svd.getS()).multiply(svd.getVT());
        Assert.assertEquals(0.0, recomposed.subtract(rm).getNorm(), 1.0e-13);
    }

    private double residual(RealMatrix a, double[] x, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.getRowDimension(); ++i) {
            double r = -b[i];
            for (int j = 0; j < a.getColumnDimension(); ++j) {
                r += a.getEntry(i, j) * x[j];
            }
            sum += r * r;
        }
        return Math.sqrt(sum);
    }
}