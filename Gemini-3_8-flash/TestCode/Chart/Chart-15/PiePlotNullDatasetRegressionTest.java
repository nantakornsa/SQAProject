package org.jfree.chart.plot.junit;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;

/**
 * Tests for the {@link PiePlot} class.
 */
public class PiePlotNullDatasetRegressionTest extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(PiePlotNullDatasetRegressionTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public PiePlotNullDatasetRegressionTest(String name) {
        super(name);
    }

    /**
     * Ensures getMaximumExplodePercent() returns 0.0 without throwing
     * a NullPointerException when the dataset is null.
     */
    public void testGetMaximumExplodePercentWithNullDataset() {
        PiePlot plot = new PiePlot(null);
        assertEquals(0.0, plot.getMaximumExplodePercent(), 0.00001);
    }

    /**
     * Draws a 3D pie chart with a null dataset to ensure it does not throw
     * a NullPointerException.
     */
    public void testDrawWithNullDataset() {
        JFreeChart chart = ChartFactory.createPieChart3D(
            "Test", null, true, false, false
        );
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(
                200, 100, BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            success = false;
        }
        assertTrue(success);
    }
}