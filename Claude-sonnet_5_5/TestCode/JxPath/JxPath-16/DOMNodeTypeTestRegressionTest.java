package org.apache.commons.jxpath.ri.model.dom;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;

/**
 * Regression test for JXPATH-114: node() node type test must match
 * all kinds of nodes (text, comments, etc.), not only elements/documents.
 */
public class DOMNodeTypeTestRegressionTest extends TestCase {

    private JXPathContext context;

    public DOMNodeTypeTestRegressionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DOMNodeTypeTestRegressionTest.class);
    }

    protected void setUp() throws Exception {
        String xml = "<root><a/>text<!--comment--><b/></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        context = JXPathContext.newContext(doc);
    }

    public void testChildAxisNodeTypeMatchesAllNodes() {
        // children of root: <a/>, text, comment, <b/>
        Object count = context.getValue("count(/root/child::node())");
        assertEquals("child::node() should match all node kinds",
                4.0, ((Number) count).doubleValue(), 0.0);
    }

    public void testFollowingAxisNodeTypeMatchesAllNodes() {
        // following of <a/>: text, comment, <b/>
        Object count = context.getValue("count(/root/a/following::node())");
        assertEquals("following::node() should match all node kinds",
                3.0, ((Number) count).doubleValue(), 0.0);
    }
}