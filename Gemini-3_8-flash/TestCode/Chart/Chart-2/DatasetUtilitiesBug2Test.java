package org.jfree.data.general.junit;

import junit.framework.TestCase;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;

/**
 * Regression test for bug 2849731 (Chart-2).
 */
public class DatasetUtilitiesBug2Test extends TestCase {

    private static final double EPSILON = 0.0000000001;

    /**
     * Test that iterateDomainBounds handles cases where startX and endX are NaN,
     * but xValue is a valid number.
     */
    public void testBug2849731_iterateDomainBounds() {
        XYIntervalSeriesCollection d = new XYIntervalSeriesCollection();
        XYIntervalSeries s = new XYIntervalSeries("S1");
        // x = 1.0, xLow = NaN, xHigh = NaN, y = NaN, yLow = 1.5, yHigh = NaN
        s.add(1.0, Double.NaN, Double.NaN, Double.NaN, 1.5, Double.NaN);
        d.addSeries(s);

        Range r = DatasetUtilities.iterateDomainBounds(d);
        assertNotNull("Range should not be null when valid x value exists", r);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(1.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Test that iterateRangeBounds handles cases where startY and endY are NaN,
     * but yValue is a valid number.
     */
    public void testBug2849731_iterateRangeBounds() {
        XYIntervalSeriesCollection d = new XYIntervalSeriesCollection();
        XYIntervalSeries s = new XYIntervalSeries("S1");
        // x = NaN, xLow = 1.5, xHigh = NaN, y = 2.0, yLow = NaN, yHigh = NaN
        s.add(Double.NaN, 1.5, Double.NaN, 2.0, Double.NaN, Double.NaN);
        d.addSeries(s);

        Range r = DatasetUtilities.iterateRangeBounds(d);
        assertNotNull("Range should not be null when valid y value exists", r);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);
    }
}