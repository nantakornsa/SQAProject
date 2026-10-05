package org.apache.commons.jxpath.ri.compiler;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;

/**
 * Regression test for JXPATH-102: floor(), ceiling() and round() must
 * preserve NaN and infinite values.
 */
public class CoreFunctionNaNInfinityRegressionTest extends TestCase {

    private JXPathContext context;

    protected void setUp() throws Exception {
        super.setUp();
        context = JXPathContext.newContext(new Object());
    }

    public void testRoundNaN() {
        Object result = context.getValue("round('NaN')");
        assertTrue(result instanceof Double);
        assertTrue("round('NaN') should be NaN but was " + result,
                ((Double) result).isNaN());
    }

    public void testRoundInfinity() {
        assertEquals(new Double(Double.NEGATIVE_INFINITY),
                context.getValue("round(-2 div 0)"));
        assertEquals(new Double(Double.POSITIVE_INFINITY),
                context.getValue("round(2 div 0)"));
    }

    public void testFloorNaNAndInfinity() {
        Object result = context.getValue("floor('NaN')");
        assertTrue(result instanceof Double);
        assertTrue("floor('NaN') should be NaN but was " + result,
                ((Double) result).isNaN());
        assertEquals(new Double(Double.NEGATIVE_INFINITY),
                context.getValue("floor(-2 div 0)"));
        assertEquals(new Double(Double.POSITIVE_INFINITY),
                context.getValue("floor(2 div 0)"));
    }

    public void testCeilingNaNAndInfinity() {
        Object result = context.getValue("ceiling('NaN')");
        assertTrue(result instanceof Double);
        assertTrue("ceiling('NaN') should be NaN but was " + result,
                ((Double) result).isNaN());
        assertEquals(new Double(Double.NEGATIVE_INFINITY),
                context.getValue("ceiling(-2 div 0)"));
        assertEquals(new Double(Double.POSITIVE_INFINITY),
                context.getValue("ceiling(2 div 0)"));
    }
}