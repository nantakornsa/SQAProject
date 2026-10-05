package org.apache.commons.jxpath.ri.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;

/**
 * Regression test for JXPATH-94: comparing an iterated multi-valued variable
 * with a literal must reset the InitialContext before evaluating.
 */
public class VariableIterateComparisonTest extends TestCase {

    private JXPathContext context;

    protected void setUp() throws Exception {
        super.setUp();
        context = JXPathContext.newContext(null);
        context.getVariables().declareVariable("d", new String[] { "a", "b" });
    }

    public void testIterateVariableThenCompare() throws Exception {
        // Iterate over the variable first
        List values = new ArrayList();
        Iterator it = context.iterate("$d");
        while (it.hasNext()) {
            values.add(it.next());
        }
        assertEquals(2, values.size());
        assertEquals("a", values.get(0));
        assertEquals("b", values.get(1));

        // Comparisons must succeed for any member of the node-set
        assertEquals(Boolean.TRUE, context.getValue("$d = 'a'"));
        assertEquals(Boolean.TRUE, context.getValue("$d = 'b'"));
    }

    public void testCompareVariableRepeatedly() throws Exception {
        assertEquals(Boolean.TRUE, context.getValue("$d = 'a'"));
        assertEquals(Boolean.TRUE, context.getValue("$d = 'b'"));
        assertEquals(Boolean.FALSE, context.getValue("$d = 'c'"));
    }
}