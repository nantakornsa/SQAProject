package org.apache.commons.math.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-552: wrong last-dimension index returned by
 * {@link MultidimensionalCounter#getCounts(int)}.
 */
public class MultidimensionalCounterRegressionTest {

    @Test
    public void testGetCountsLastDimension() {
        final MultidimensionalCounter c = new MultidimensionalCounter(2, 3, 4);

        // Specific values known to fail on the buggy version.
        Assert.assertArrayEquals(new int[] { 0, 0, 3 }, c.getCounts(3));
        Assert.assertArrayEquals(new int[] { 0, 1, 2 }, c.getCounts(6));
        Assert.assertArrayEquals(new int[] { 0, 2, 3 }, c.getCounts(11));
        Assert.assertArrayEquals(new int[] { 1, 2, 3 }, c.getCounts(23));
    }

    @Test
    public void testGetCountsRoundTrip() {
        final int[] sizes = { 2, 3, 4 };
        final MultidimensionalCounter c = new MultidimensionalCounter(sizes[0], sizes[1], sizes[2]);

        int index = 0;
        for (int i = 0; i < sizes[0]; i++) {
            for (int j = 0; j < sizes[1]; j++) {
                for (int k = 0; k < sizes[2]; k++) {
                    final int[] expected = { i, j, k };
                    final int[] actual = c.getCounts(index);
                    Assert.assertArrayEquals("Wrong multidimensional index for " + index,
                                             expected, actual);
                    Assert.assertEquals("Wrong unidimensional index for " + index,
                                        index, c.getCount(expected));
                    ++index;
                }
            }
        }
        Assert.assertEquals(c.getSize(), index);
    }
}