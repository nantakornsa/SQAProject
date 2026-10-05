package org.apache.commons.codec.language;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DoubleMetaphoneCodec3Test extends TestCase {

    public static Test suite() {
        return new TestSuite(DoubleMetaphoneCodec3Test.class);
    }

    public DoubleMetaphoneCodec3Test(String name) {
        super(name);
    }

    public void testDoubleMetaphoneAlternateAngier() {
        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
        assertEquals("ANJR", doubleMetaphone.doubleMetaphone("Angier", true));
    }
}