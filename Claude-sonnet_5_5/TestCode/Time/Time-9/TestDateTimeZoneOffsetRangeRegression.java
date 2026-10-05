package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for DateTimeZone offset range validation.
 */
public class TestDateTimeZoneOffsetRangeRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestDateTimeZoneOffsetRangeRegression.class);
    }

    public TestDateTimeZoneOffsetRangeRegression(String name) {
        super(name);
    }

    public void testForOffsetHoursMinutes_outOfRangeHours() {
        try {
            DateTimeZone.forOffsetHoursMinutes(24, 0);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
        try {
            DateTimeZone.forOffsetHoursMinutes(-24, 0);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
        try {
            DateTimeZone.forOffsetHoursMinutes(Integer.MAX_VALUE, 0);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    public void testForOffsetHoursMinutes_boundaries() {
        assertEquals(DateTimeZone.forID("+23:59"), DateTimeZone.forOffsetHoursMinutes(23, 59));
        assertEquals(DateTimeZone.forID("-23:59"), DateTimeZone.forOffsetHoursMinutes(-23, 59));
    }

    public void testForOffsetMillis_outOfRange() {
        try {
            DateTimeZone.forOffsetMillis(86400 * 1000);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
        try {
            DateTimeZone.forOffsetMillis(-86400 * 1000);
            fail();
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}