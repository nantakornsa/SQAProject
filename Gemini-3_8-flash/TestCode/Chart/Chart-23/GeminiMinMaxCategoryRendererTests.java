package org.jfree.chart.renderer.category.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;

/**
 * Tests for the {@link MinMaxCategoryRenderer} class.
 */
public class GeminiMinMaxCategoryRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiMinMaxCategoryRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiMinMaxCategoryRendererTests(String name) {
        super(name);
    }

    /**
     * Test the equals() method.
     */
    public void testEquals() {
        MinMaxCategoryRenderer r1 = new MinMaxCategoryRenderer();
        MinMaxCategoryRenderer r2 = new MinMaxCategoryRenderer();
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));

        r1.setDrawLines(true);
        assertFalse(r1.equals(r2));
        r2.setDrawLines(true);
        assertTrue(r1.equals(r2));

        r1.setGroupPaint(Color.red);
        assertFalse(r1.equals(r2));
        r2.setGroupPaint(Color.red);
        assertTrue(r1.equals(r2));

        r1.setGroupStroke(new BasicStroke(1.5f));
        assertFalse(r1.equals(r2));
        r2.setGroupStroke(new BasicStroke(1.5f));
        assertTrue(r1.equals(r2));
    }
}