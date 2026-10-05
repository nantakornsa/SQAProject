package org.apache.commons.codec.language;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.StringEncoderAbstractTest;

public class CaverphoneTest extends StringEncoderAbstractTest {

    public static Test suite() {
        return new TestSuite(CaverphoneTest.class);
    }

    public CaverphoneTest(String name) {
        super(name);
    }

    protected StringEncoder makeEncoder() {
        return new Caverphone();
    }

    public void testLocaleIndependence() throws Exception {
        StringEncoder encoder = makeEncoder();
        String[] data = { "I", "i" };

        Locale orig = Locale.getDefault();
        try {
            for (int i = 0; i < data.length; i++) {
                Locale.setDefault(Locale.ENGLISH);
                String ref = encoder.encode(data[i]);

                Locale.setDefault(new Locale("tr"));
                String cur = encoder.encode(data[i]);

                assertEquals("tr locale, input '" + data[i] + "': ", ref, cur);
            }
        } finally {
            Locale.setDefault(orig);
        }
    }
}