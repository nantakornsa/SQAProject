package org.apache.commons.math.linear;

import junit.framework.TestCase;

import org.apache.commons.math.MathException;

public class CholeskyDecompositionMath274RegressionTest extends TestCase {

    public void testMath274NotPositiveDefinite() throws MathException {
        RealMatrix matrix = MatrixUtils.createRealMatrix(new double[][] {
                { 0.40434286, -0.09376327, 0.30328980, 0.04909388 },
                {-0.09376327,  0.10400408, 0.07137959, 0.04762857 },
                { 0.30328980,  0.07137959, 0.30458776, 0.04882449 },
                { 0.04909388,  0.04762857, 0.04882449, 0.07543265 }
        });
        try {
            new CholeskyDecompositionImpl(matrix);
            fail("Expected NotPositiveDefiniteMatrixException");
        } catch (NotPositiveDefiniteMatrixException e) {
            // expected
        }
    }

    public void testNotPositiveDefiniteSimple() throws MathException {
        RealMatrix matrix = MatrixUtils.createRealMatrix(new double[][] {
                { 14, 11,  13, 15, 24 },
                { 11, 34,  13, 8,  25 },
                { 13, 13, -76, 17, 21 },
                { 15, 8,   17, 63, 1  },
                { 24, 25,  21, 1,  57 }
        });
        try {
            new CholeskyDecompositionImpl(matrix);
            fail("Expected NotPositiveDefiniteMatrixException");
        } catch (NotPositiveDefiniteMatrixException e) {
            // expected
        }
    }
}