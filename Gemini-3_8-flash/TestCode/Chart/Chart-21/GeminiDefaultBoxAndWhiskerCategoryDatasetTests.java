package org.jfree.data.statistics.junit;

import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.data.Range;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

/**
 * Tests for the {@link DefaultBoxAndWhiskerCategoryDataset} class.
 */
public class GeminiDefaultBoxAndWhiskerCategoryDatasetTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiDefaultBoxAndWhiskerCategoryDatasetTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiDefaultBoxAndWhiskerCategoryDatasetTests(String name) {
        super(name);
    }

    /**
     * A test to ensure that range bounds are updated correctly when an existing
     * item that defines the minimum range value is replaced.
     */
    public void testGetRangeBounds() {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        dataset.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 
                new ArrayList()), "R1", "C1");
        assertEquals(new Range(7.0, 8.0), dataset.getRangeBounds(false));

        dataset.add(new BoxAndWhiskerItem(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 
                new ArrayList()), "R1", "C1");
        assertEquals(new Range(7.5, 8.5), dataset.getRangeBounds(false));

        dataset.add(new BoxAndWhiskerItem(2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 
                new ArrayList()), "R2", "C1");
        assertEquals(new Range(7.5, 9.5), dataset.getRangeBounds(false));

        // Replacing the item at R1, C1 which previously had the minimum value 7.5.
        // The new minimum value in R1, C1 is 8.6, but R2, C1 has minimum value 8.5,
        // so the new overall minimum should be 8.5.
        dataset.add(new BoxAndWhiskerItem(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 8.6, 9.6, 
                new ArrayList()), "R1", "C1");
        assertEquals(new Range(8.5, 9.6), dataset.getRangeBounds(false));
        assertEquals(new Range(8.5, 9.6), dataset.getRangeBounds(true));
    }
}