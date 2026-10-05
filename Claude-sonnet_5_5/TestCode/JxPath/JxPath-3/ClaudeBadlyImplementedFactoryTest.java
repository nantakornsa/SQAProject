package org.apache.commons.jxpath.ri.model.beans;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.jxpath.AbstractFactory;
import org.apache.commons.jxpath.JXPathAbstractFactoryException;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.apache.commons.jxpath.Pointer;

/**
 * Regression test for JXPATH-68: a factory that reports success without
 * actually creating the object must not cause endless recursion.
 */
public class ClaudeBadlyImplementedFactoryTest extends TestCase {

    private JXPathContext context;

    protected void setUp() throws Exception {
        super.setUp();
        context = JXPathContext.newContext(new HashMap());
        context.setFactory(new AbstractFactory() {
            public boolean createObject(JXPathContext context, Pointer pointer,
                    Object parent, String name, int index) {
                // claims success but creates nothing
                return true;
            }
        });
    }

    public void testBadFactoryImplementation() {
        try {
            context.createPath("foo/bar");
            fail("should fail with JXPathException caused by JXPathAbstractFactoryException");
        } catch (JXPathException e) {
            assertTrue(e.getCause() instanceof JXPathAbstractFactoryException);
        }
    }
}