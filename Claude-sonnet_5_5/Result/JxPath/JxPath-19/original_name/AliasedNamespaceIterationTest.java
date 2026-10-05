package org.apache.commons.jxpath.ri.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.xml.sax.InputSource;

/**
 * Regression test: position of an element among its siblings must be computed
 * from namespace URI and local name, not from the (prefixed) node name.
 */
public class AliasedNamespaceIterationTest extends TestCase {

    private static final String XML =
        "<p:doc xmlns:p=\"foo\">"
            + "<p:elem>one</p:elem>"
            + "<q:elem xmlns:q=\"foo\">two</q:elem>"
            + "</p:doc>";

    private JXPathContext createContext(Object root) {
        JXPathContext context = JXPathContext.newContext(root);
        context.registerNamespace("a", "foo");
        return context;
    }

    private void assertPointerPaths(JXPathContext context) {
        List expected = new ArrayList();
        expected.add("/a:doc[1]/a:elem[1]");
        expected.add("/a:doc[1]/a:elem[2]");

        List actual = new ArrayList();
        Iterator iter = context.iteratePointers("/a:doc/a:elem");
        while (iter.hasNext()) {
            actual.add(((Pointer) iter.next()).asPath());
        }
        assertEquals("Evaluating pointer iterator </a:doc/a:elem>",
                expected, actual);
    }

    public void testIterateDOM() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Object doc = builder.parse(new InputSource(new StringReader(XML)));
        assertPointerPaths(createContext(doc));
    }

    public void testIterateJDOM() throws Exception {
        Object doc = new org.jdom.input.SAXBuilder()
                .build(new StringReader(XML));
        assertPointerPaths(createContext(doc));
    }
}