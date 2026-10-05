package org.joda.time;

import junit.framework.TestCase;

/**
 * Regression test for Joda-Time bug 60 (Time-26): field setters lost the
 * time zone offset when run on an overlap (DST autumn cutover) instant.
 */
public class TestDateTimeZoneCutoverRegression extends TestCase {

    public void testBug2182444_usCentral_setFieldsInOverlap() {
        Chronology chronUSCentral = org.joda.time.chrono.ISOChronology.getInstance(DateTimeZone.forID("US/Central"));
        Chronology chronUTC = org.joda.time.chrono.ISOChronology.getInstanceUTC();

        DateTime usCentralStandardInUTC = new DateTime(2008, 11, 2, 7, 0, 0, 0, chronUTC);
        DateTime usCentralDaylightInUTC = new DateTime(2008, 11, 2, 6, 0, 0, 0, chronUTC);
        assertTrue("Should be standard time",
                chronUSCentral.getZone().isStandardOffset(usCentralStandardInUTC.getMillis()));
        assertFalse("Should be daylight time",
                chronUSCentral.getZone().isStandardOffset(usCentralDaylightInUTC.getMillis()));

        DateTime usCentralStandardInUSCentral = usCentralStandardInUTC.toDateTime(chronUSCentral);
        DateTime usCentralDaylightInUSCentral = usCentralDaylightInUTC.toDateTime(chronUSCentral);

        assertEquals(1, usCentralStandardInUSCentral.getHourOfDay());
        assertEquals(usCentralStandardInUSCentral.getHourOfDay(), usCentralDaylightInUSCentral.getHourOfDay());
        assertTrue(usCentralStandardInUSCentral.getMillis() != usCentralDaylightInUSCentral.getMillis());

        assertEquals(usCentralStandardInUSCentral, usCentralStandardInUSCentral.withHourOfDay(1));
        assertEquals(usCentralStandardInUSCentral, usCentralStandardInUSCentral.withMinuteOfHour(0));
        assertEquals(usCentralStandardInUSCentral, usCentralStandardInUSCentral.withSecondOfMinute(0));
        assertEquals(usCentralStandardInUSCentral, usCentralStandardInUSCentral.withMillisOfSecond(0));

        assertEquals(usCentralDaylightInUSCentral, usCentralDaylightInUSCentral.withHourOfDay(1));
        assertEquals(usCentralDaylightInUSCentral, usCentralDaylightInUSCentral.withMinuteOfHour(0));
        assertEquals(usCentralDaylightInUSCentral, usCentralDaylightInUSCentral.withSecondOfMinute(0));
        assertEquals(usCentralDaylightInUSCentral, usCentralDaylightInUSCentral.withMillisOfSecond(0));
    }

    public void testBug2182444_ausNSW_setFieldsInOverlap() {
        Chronology chronAusNSW = org.joda.time.chrono.ISOChronology.getInstance(DateTimeZone.forID("Australia/NSW"));
        Chronology chronUTC = org.joda.time.chrono.ISOChronology.getInstanceUTC();

        DateTime nswStandardInUTC = new DateTime(2008, 4, 5, 16, 0, 0, 0, chronUTC);
        DateTime nswDaylightInUTC = new DateTime(2008, 4, 5, 15, 0, 0, 0, chronUTC);

        DateTime standard = nswStandardInUTC.toDateTime(chronAusNSW);
        DateTime daylight = nswDaylightInUTC.toDateTime(chronAusNSW);

        assertEquals(2, standard.getHourOfDay());
        assertEquals(2, daylight.getHourOfDay());
        assertTrue(standard.getMillis() != daylight.getMillis());

        assertEquals(standard, standard.withMinuteOfHour(0));
        assertEquals(standard, standard.withSecondOfMinute(0));
        assertEquals(standard, standard.withMillisOfSecond(0));

        assertEquals(daylight, daylight.withMinuteOfHour(0));
        assertEquals(daylight, daylight.withSecondOfMinute(0));
        assertEquals(daylight, daylight.withMillisOfSecond(0));
    }
}