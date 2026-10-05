package org.apache.commons.jxpath.ri.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.jxpath.BasicNodeSet;
import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.ExpressionContext;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.NodeSet;

/**
 * Regression test: extension functions returning a NodeSet must be iterated
 * as the nodes' values rather than as a single NodeSet value.
 */
public class ExtensionFunctionNodeSetRegressionTest extends TestCase {

    public static class Funcs {
        public static NodeSet nodeSet(ExpressionContext ctx) {
            BasicNodeSet result = new BasicNodeSet();
            JXPathContext jxc = ctx.getJXPathContext();
            result.add(jxc.getPointer("/beans[1]"));
            result.add(jxc.getPointer("/beans[2]"));
            return result;
        }
    }

    private JXPathContext context;
    private Map bean1;
    private Map bean2;

    protected void setUp() throws Exception {
        super.setUp();
        bean1 = new HashMap();
        bean1.put("name", "Name 1");
        bean2 = new HashMap();
        bean2.put("name", "Name 2");
        List beans = new ArrayList();
        beans.add(bean1);
        beans.add(bean2);
        Map root = new HashMap();
        root.put("beans", beans);

        context = JXPathContext.newContext(root);
        context.setFunctions(new ClassFunctions(Funcs.class, "test"));
    }

    public void testNodeSetReturnIterate() {
        List actual = new ArrayList();
        Iterator it = context.iterate("test:nodeSet()");
        while (it.hasNext()) {
            actual.add(it.next());
        }
        assertEquals("Number of iterated values", 2, actual.size());
        assertSame(bean1, actual.get(0));
        assertSame(bean2, actual.get(1));
    }

    public void testNodeSetReturnPathIterate() {
        List actual = new ArrayList();
        Iterator it = context.iterate("test:nodeSet()/name");
        while (it.hasNext()) {
            actual.add(it.next());
        }
        assertEquals(2, actual.size());
        assertEquals("Name 1", actual.get(0));
        assertEquals("Name 2", actual.get(1));
    }

    public void testNodeSetReturnPointerIterate() {
        List actual = new ArrayList();
        Iterator it = context.iteratePointers("test:nodeSet()");
        while (it.hasNext()) {
            actual.add(it.next().toString());
        }
        assertEquals(2, actual.size());
        assertEquals("/beans[1]", actual.get(0));
        assertEquals("/beans[2]", actual.get(1));
    }
}