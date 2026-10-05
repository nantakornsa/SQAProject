package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestDateTimeZoneOffsetRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestDateTimeZoneOffsetRegression.class);
    }

    public TestDateTimeZoneOffsetRegression(String name) {
        super(name);
    }

    public void testForOffsetHoursMinutes_negativeMinutes() {
        assertEquals(DateTimeZone.forID("-00:15"), DateTimeZone.forOffsetHoursMinutes(0, -15));
        assertEquals(DateTimeZone.forID("-02:15"), DateTimeZone.forOffsetHoursMinutes(-2, -15));
        assertEquals(DateTimeZone.forID("-02:15"), DateTimeZone.forOffsetHoursMinutes(-2, 15));
        try {
            DateTimeZone.forOffsetHoursMinutes(2, -15);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}