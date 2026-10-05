package org.joda.time.chrono;

import junit.framework.TestCase;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;

/**
 * Regression test for GJChronology leap year handling during construction
 * of dates that are valid only in the Julian calendar.
 */
public class TestGJChronologyLeapYearRegression extends TestCase {

    public TestGJChronologyLeapYearRegression(String name) {
        super(name);
    }

    public void testLeapYearRulesConstruction() {
        // 1500 not leap in Gregorian, but is leap in Julian
        DateMidnight dt = new DateMidnight(1500, 2, 29, GJChronology.getInstanceUTC());
        assertEquals(1500, dt.getYear());
        assertEquals(2, dt.getMonthOfYear());
        assertEquals(29, dt.getDayOfMonth());
    }

    public void testInvalidLeapDayAfterCutoverStillFails() {
        // 1900 is not a leap year in Gregorian, and is after the cutover
        try {
            new DateMidnight(1900, 2, 29, GJChronology.getInstance(DateTimeZone.UTC));
            fail();
        } catch (IllegalFieldValueException ex) {
            // expected
        }
    }
}