package org.apache.commons.lang.time;

import junit.framework.TestCase;
import org.apache.commons.lang.SerializationUtils;

/**
 * Regression test for Lang-56 (LANG-303): FastDateFormat serialization failure.
 */
public class FastDateFormatLang56Test extends TestCase {

    public FastDateFormatLang56Test(String name) {
        super(name);
    }

    public void testSerialization() {
        FastDateFormat format = FastDateFormat.getInstance("yyyy/MM/dd");
        FastDateFormat deserialized = (FastDateFormat) SerializationUtils.deserialize(SerializationUtils.serialize(format));
        assertEquals(format, deserialized);
    }
}