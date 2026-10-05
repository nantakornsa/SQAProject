package org.apache.commons.jxpath.ri.compiler;

import java.io.StringReader;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class VariableUnionRegressionTest extends TestCase {

    public void testUnionOfVariableAndNode() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(
                        new InputSource(new StringReader(
                                "<MAIN><A/><A/></MAIN>")));

        JXPathContext context = JXPathContext.newContext(doc);
        context.getVariables().declareVariable("var", "varValue");
        int sz = 0;
        for (Iterator ptrs = context.iteratePointers("$var | /MAIN/A"); ptrs.hasNext(); sz++) {
            ptrs.next();
        }
        assertEquals(3, sz);
    }
}