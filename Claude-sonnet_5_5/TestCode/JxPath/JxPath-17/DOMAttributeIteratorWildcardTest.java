package org.apache.commons.jxpath.ri.model.dom;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;

/**
 * Regression test for JXPATH-109: the wildcard attribute axis (@*) on a DOM
 * element skipped attributes that carry a namespace prefix.
 */
public class DOMAttributeIteratorWildcardTest extends TestCase {

    public DOMAttributeIteratorWildcardTest(String name) {
        super(name);
    }

    public void testWildcardAttributeAxisIncludesPrefixedAttributes()
            throws Exception {
        String xml = "<root xmlns:p=\"urn:test:p\">"
                + "<p:amount p:discount=\"10%\" discount=\"20%\"/>"
                + "</root>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));

        JXPathContext context = JXPathContext.newContext(doc);

        List values = new ArrayList();
        Iterator it = context.iterate("/root/*/@*");
        while (it.hasNext()) {
            values.add(it.next());
        }

        assertEquals("Wrong number of attributes: " + values, 2, values.size());
        assertTrue("Missing prefixed attribute value: " + values,
                values.contains("10%"));
        assertTrue("Missing unprefixed attribute value: " + values,
                values.contains("20%"));
    }
}