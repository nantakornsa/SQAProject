package org.apache.commons.jxpath.ri.model;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;

/**
 * Regression test for JXPATH-151: comparing map entries where one value is null.
 */
public class ClaudeJXPath151Test extends TestCase {

    private JXPathContext context;

    protected void setUp() throws Exception {
        super.setUp();
        Map map = new HashMap();
        map.put("a", Integer.valueOf(1));
        map.put("b", null);
        map.put("c", Integer.valueOf(1));

        Map root = new HashMap();
        root.put("map", map);

        context = JXPathContext.newContext(root);
    }

    private void assertXPathBoolean(String xpath, Boolean expected) {
        Object actual = context.getValue(xpath);
        assertEquals("Evaluating <" + xpath + ">", expected, actual);
    }

    public void testMapValueEquality() {
        assertXPathBoolean("map/b != map/a", Boolean.TRUE);
        assertXPathBoolean("map/a != map/b", Boolean.TRUE);
        assertXPathBoolean("map/a != map/c", Boolean.FALSE);
        assertXPathBoolean("map/a = map/b", Boolean.FALSE);
        assertXPathBoolean("map/a = map/c", Boolean.TRUE);
        assertXPathBoolean("not(map/a = map/b)", Boolean.TRUE);
        assertXPathBoolean("not(map/a = map/c)", Boolean.FALSE);
    }
}