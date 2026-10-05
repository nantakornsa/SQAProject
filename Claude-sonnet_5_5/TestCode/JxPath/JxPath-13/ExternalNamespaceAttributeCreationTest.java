package org.apache.commons.jxpath.ri.model;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Regression test for JXPATH-97: creating an attribute whose prefix is only
 * registered externally on the JXPathContext (not declared in the DOM document)
 * must resolve the prefix through the context's namespace resolver.
 */
public class ExternalNamespaceAttributeCreationTest extends TestCase {

    public void testCreateAndSetAttributeWithExternallyRegisteredPrefixDOM()
            throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader("<root/>")));

        JXPathContext context = JXPathContext.newContext(doc);
        context.registerNamespace("A", "foo");

        context.createPathAndSetValue("/root/@A:bar", "value");

        Element root = doc.getDocumentElement();
        assertEquals("value", root.getAttributeNS("foo", "bar"));
        assertEquals("value", context.getValue("/root/@A:bar"));
    }
}