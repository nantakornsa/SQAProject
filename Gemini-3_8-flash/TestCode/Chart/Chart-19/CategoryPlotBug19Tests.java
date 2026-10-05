package org.jfree.chart.plot.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;

/**
 * Regression test for bug Chart-19 in CategoryPlot.
 */
public class CategoryPlotBug19Tests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CategoryPlotBug19Tests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CategoryPlotBug19Tests(String name) {
        super(name);
    }

    /**
     * Tests that getDomainAxisIndex throws IllegalArgumentException for null argument.
     */
    public void testGetDomainAxisIndexNull() {
        CategoryPlot plot = new CategoryPlot(null, new CategoryAxis("X"), new NumberAxis("Y"), null);
        try {
            plot.getDomainAxisIndex(null);
            fail("Expected IllegalArgumentException on getDomainAxisIndex(null)");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests that getRangeAxisIndex throws IllegalArgumentException for null argument.
     */
    public void testGetRangeAxisIndexNull() {
        CategoryPlot plot = new CategoryPlot(null, new CategoryAxis("X"), new NumberAxis("Y"), null);
        try {
            plot.getRangeAxisIndex(null);
            fail("Expected IllegalArgumentException on getRangeAxisIndex(null)");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }
}