package org.jfree.chart.axis.junit;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tests for the {@link org.jfree.chart.axis.Axis} class.
 */
public class GeminiAxisTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiAxisTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiAxisTests(String name) {
        super(name);
    }

    /**
     * A test to ensure that drawing a chart with null PlotRenderingInfo or
     * ChartRenderingInfo does not throw a NullPointerException when drawing
     * the axis label.
     */
    public void testDrawWithNullInfo() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Row 1", "Column 1");
        dataset.addValue(5.0, "Row 1", "Column 2");
        JFreeChart chart = ChartFactory.createBarChart(
            "Test Chart",
            "Category Axis",
            "Value Axis",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            false,
            false
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