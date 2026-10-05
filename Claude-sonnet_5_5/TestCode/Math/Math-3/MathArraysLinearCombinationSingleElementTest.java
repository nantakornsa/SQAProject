package org.apache.commons.math3.util;

import org.junit.Assert;
import org.junit.Test;

public class MathArraysLinearCombinationSingleElementTest {

    @Test
    public void testLinearCombinationWithSingleElementArray() {
        final double[] a = { 1.23456789 };
        final double[] b = { 98765432.1 };

        Assert.assertEquals(a[0] * b[0], MathArrays.linearCombination(a, b), 0d);
    }

    @Test
    public void testLinearCombinationWithSingleElementArrayNegativeValues() {
        final double[] a = { -3.14159265 };
        final double[] b = { 2718281.828 };

        Assert.assertEquals(a[0] * b[0], MathArrays.linearCombination(a, b), 0d);
    }
}