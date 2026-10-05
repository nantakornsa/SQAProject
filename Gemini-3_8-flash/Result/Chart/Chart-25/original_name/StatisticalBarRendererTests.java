package org.jfree.chart.renderer.category.junit;

import java.awt.image.BufferedImage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

/**
 * Tests for the {@link StatisticalBarRenderer} class.
 */
public class StatisticalBarRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StatisticalBarRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StatisticalBarRendererTests(String name) {
        super(name);
    }

    /**
     * Draws a chart where the dataset has a null mean value in vertical orientation.
     */
    public void testDrawWithNullMeanVertical() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset 
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(null, new Double(4.0), "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset, 
                    new CategoryAxis("Category"), new NumberAxis("Value"), 
                    new StatisticalBarRenderer());
            JFreeChart chart = new JFreeChart(plot);
            chart.createBufferedImage(300, 200, null);
            success = true;
        }
        catch (NullPointerException e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * Draws a chart where the dataset has a null standard deviation value in vertical orientation.
     */
    public void testDrawWithNullDeviationVertical() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset 
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(new Double(4.0), null, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset, 
                    new CategoryAxis("Category"), new NumberAxis("Value"), 
                    new StatisticalBarRenderer());
            JFreeChart chart = new JFreeChart(plot);
            chart.createBufferedImage(300, 200, null);
            success = true;
        }
        catch (NullPointerException e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * Draws a chart where the dataset has a null mean value in horizontal orientation.
     */
    public void testDrawWithNullMeanHorizontal() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset 
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(null, new Double(4.0), "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset, 
                    new CategoryAxis("Category"), new NumberAxis("Value"), 
                    new StatisticalBarRenderer());
            plot.setOrientation(PlotOrientation.HORIZONTAL);
            JFreeChart chart = new JFreeChart(plot);
            chart.createBufferedImage(300, 200, null);
            success = true;
        }
        catch (NullPointerException e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * Draws a chart where the dataset has a null standard deviation value in horizontal orientation.
     */
    public void testDrawWithNullDeviationHorizontal() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset 
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(new Double(4.0), null, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset, 
                    new CategoryAxis("Category"), new NumberAxis("Value"), 
                    new StatisticalBarRenderer());
            plot.setOrientation(PlotOrientation.HORIZONTAL);
            JFreeChart chart = new JFreeChart(plot);
            chart.createBufferedImage(300, 200, null);
            success = true;
        }
        catch (NullPointerException e) {
            success = false;
        }
        assertTrue(success);
    }
}