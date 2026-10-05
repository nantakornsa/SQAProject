package org.joda.time;

import junit.framework.TestCase;

/**
 * Regression test for the London autumn cutover overlap (Time-19).
 */
public class TestDateTimeZoneCutoverRegression extends TestCase {

    public void testDateTimeCreation_london_overlap() {
        DateTimeZone zone = DateTimeZone.forID("Europe/London");
        DateTime base = new DateTime(2011, 10, 30, 1, 15, zone);
        assertEquals("2011-10-30T01:15:00.000+01:00", base.toString());
        assertEquals("2011-10-30T01:15:00.000Z", base.plusHours(1).toString());
    }

    public void testGetOffsetFromLocal_london_overlap() {
        DateTimeZone zone = DateTimeZone.forID("Europe/London");
        // 2011-10-30T01:15 local, as if UTC
        long local = new DateTime(2011, 10, 30, 1, 15, DateTimeZone.UTC).getMillis();
        // the earlier (summer time) offset should be chosen in an overlap
        assertEquals(3600000, zone.getOffsetFromLocal(local));
    }
}