package org.apache.commons.codec.language;

import junit.framework.TestCase;

public class DoubleMetaphoneCodec3Test extends TestCase {

    public DoubleMetaphoneCodec3Test(String name) {
        super(name);
    }

    public void testDoubleMetaphoneAlternateAngier() {
        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
        assertEquals("ANJR", doubleMetaphone.doubleMetaphone("Angier", true));
    }
}