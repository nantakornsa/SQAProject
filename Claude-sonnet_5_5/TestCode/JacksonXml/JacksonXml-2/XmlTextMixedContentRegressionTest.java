package com.fasterxml.jackson.dataformat.xml.misc;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.XmlTestBase;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class XmlTextMixedContentRegressionTest extends XmlTestBase
{
    static class MixedBean {
        @JacksonXmlText
        public String text;

        public int age;
    }

    private final XmlMapper MAPPER = new XmlMapper();

    // [dataformat-xml#196]: text preceding a child element must not be dropped
    public void testMixedContent() throws Exception
    {
        MixedBean bean = MAPPER.readValue(
                "<MixedBean>some text<age>27</age></MixedBean>",
                MixedBean.class);
        assertEquals(27, bean.age);
        assertEquals("some text", bean.text);
    }

    // whitespace-only text before a child element is still ignored
    public void testWhitespaceBeforeElement() throws Exception
    {
        MixedBean bean = MAPPER.readValue(
                "<MixedBean>\n   <age>27</age></MixedBean>",
                MixedBean.class);
        assertEquals(27, bean.age);
    }
}