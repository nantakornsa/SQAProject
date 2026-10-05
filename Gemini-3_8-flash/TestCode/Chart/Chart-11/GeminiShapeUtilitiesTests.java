package org.jfree.chart.util.junit;

import java.awt.geom.GeneralPath;
import junit.framework.TestCase;
import org.jfree.chart.util.ShapeUtilities;

/**
 * Regression test for bug Chart-11.
 */
public class GeminiShapeUtilitiesTests extends TestCase {

    /**
     * Tests ShapeUtilities.equal(GeneralPath, GeneralPath) with two different paths.
     * In the buggy version, iterator2 was initialized with p1 instead of p2,
     * causing equal() to return true even when the paths differed.
     */
    public void testEqualGeneralPathsBug11() {
        GeneralPath g1 = new GeneralPath();
        g1.moveTo(1.0f, 2.0f);
        g1.lineTo(3.0f, 4.0f);
        g1.closePath();

        GeneralPath g2 = new GeneralPath();
        g2.moveTo(11.0f, 22.0f);
        g2.lineTo(3.0f, 4.0f);
        g2.closePath();

        assertFalse("Paths with different coordinates should not be equal", ShapeUtilities.equal(g1, g2));
    }
}