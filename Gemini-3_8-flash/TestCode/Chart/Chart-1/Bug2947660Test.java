package org.jfree.chart.renderer.category.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Regression test for bug 2947660 / Chart-1 in JFreeChart.
 */
public class Bug2947660Test extends TestCase {

    public Bug2947660Test(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(Bug2947660Test.class);
    }

    /**
     * Test that getLegendItems() returns legend items when a dataset with series is present.
     */
    public void test2947660() {
        AbstractCategoryItemRenderer r = new LineAndShapeRenderer();
        assertNotNull(r.getLegendItems());
        assertEquals(0, r.getLegendItems().getItemCount());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(dataset);
        plot.setRenderer(r);
        assertEquals(0, r.getLegendItems().getItemCount());

        dataset.addValue(1.0, "S1", "C1");
        LegendItemCollection lic = r.getLegendItems();
        assertEquals(1, lic.getItemCount());
        assertEquals("S1", lic.get(0).getLabel());
    }
}