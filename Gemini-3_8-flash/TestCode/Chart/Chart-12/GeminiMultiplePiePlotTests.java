package org.jfree.chart.plot.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tests for the {@link MultiplePiePlot} class.
 */
public class GeminiMultiplePiePlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiMultiplePiePlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiMultiplePiePlotTests(String name) {
        super(name);
    }

    /**
     * Tests the constructor to verify that the plot registers itself as a
     * change listener with the dataset passed to it.
     */
    public void testConstructor() {
        MultiplePiePlot plot = new MultiplePiePlot();
        assertNull(plot.getDataset());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        plot = new MultiplePiePlot(dataset);
        assertSame(dataset, plot.getDataset());
        assertTrue(dataset.hasListener(plot));
    }
}