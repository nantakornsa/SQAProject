package org.apache.commons.jxpath.ri.axes;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;

/**
 * Regression test for JXPATH-115: attribute::node() must select attributes.
 */
public class AttributeContextNodeTypeTest extends TestCase {

    public AttributeContextNodeTypeTest(String name) {
        super(name);
    }

    public void testAttributeAxisWithNodeTypeTest() throws Exception {
        String xml = "<root><item first=\"10%\" second=\"20%\"/></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().parse(
                new ByteArrayInputStream(xml.getBytes("UTF-8")));

        JXPathContext context = JXPathContext.newContext(doc);

        Set actual = new TreeSet();
        Iterator it = context.iterate("root/item/attribute::node()");
        while (it.hasNext()) {
            actual.add(String.valueOf(it.next()));
        }

        Set expected = new TreeSet();
        expected.add("10%");
        expected.add("20%");

        assertEquals("attribute::node() should return all attributes",
                expected, actual);
    }
}