package org.apache.commons.jxpath.ri.model;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Regression test for JXPATH-154: an inner empty default namespace
 * declaration (xmlns="") must be treated as "no namespace".
 */
public class ClaudeJXPath154Test extends TestCase {

    public void testInnerEmptyNamespaceDOM() throws Exception {
        String xml = "<b:foo xmlns:b=\"ns:b\" xmlns=\"\"><test/></b:foo>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));

        JXPathContext context = JXPathContext.newContext(doc);
        context.registerNamespace("b", "ns:b");

        Pointer pointer = context.getPointer("b:foo/test");
        assertEquals("/b:foo[1]/test[1]", pointer.asPath());
    }
}