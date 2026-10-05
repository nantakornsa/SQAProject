package org.jfree.chart.plot.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Layer;

/**
 * Regression test to ensure removing markers when none have been added
 * does not throw a NullPointerException.
 */
public class MarkerRemovalRegressionTests extends TestCase {

    /**
     * Returns the tests as a suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MarkerRemovalRegressionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MarkerRemovalRegressionTests(String name) {
        super(name);
    }

    /**
     * Test removing domain and range markers from CategoryPlot when no markers exist.
     */
    public void testCategoryPlotRemoveMarkerWhenEmpty() {
        CategoryPlot plot = new CategoryPlot();
        try {
            boolean removedDomain = plot.removeDomainMarker(new ValueMarker(1.0), Layer.FOREGROUND);
            assertFalse(removedDomain);

            removedDomain = plot.removeDomainMarker(new ValueMarker(1.0), Layer.BACKGROUND);
            assertFalse(removedDomain);

            boolean removedRange = plot.removeRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
            assertFalse(removedRange);

            removedRange = plot.removeRangeMarker(new ValueMarker(1.0), Layer.BACKGROUND);
            assertFalse(removedRange);
        } catch (NullPointerException e) {
            fail("NullPointerException thrown when removing marker from CategoryPlot with no markers: " + e.getMessage());
        }
    }

    /**
     * Test removing domain and range markers from XYPlot when no markers exist.
     */
    public void testXYPlotRemoveMarkerWhenEmpty() {
        XYPlot plot = new XYPlot();
        try {
            boolean removedDomain = plot.removeDomainMarker(new ValueMarker(1.0), Layer.FOREGROUND);
            assertFalse(removedDomain);

            removedDomain = plot.removeDomainMarker(new ValueMarker(1.0), Layer.BACKGROUND);
            assertFalse(removedDomain);

            boolean removedRange = plot.removeRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
            assertFalse(removedRange);

            removedRange = plot.removeRangeMarker(new ValueMarker(1.0), Layer.BACKGROUND);
            assertFalse(removedRange);
        } catch (NullPointerException e) {
            fail("NullPointerException thrown when removing marker from XYPlot with no markers: " + e.getMessage());
        }
    }
}