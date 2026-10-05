package org.joda.time;

import java.util.TimeZone;

import junit.framework.TestCase;

/**
 * Regression test for Joda-Time bug 112: old-style JDK time zone IDs such as
 * "WET" were converted to a different zone (Europe/London) instead of the
 * matching "WET" zone that exists in the tz database.
 */
public class TestDateTimeZoneOldIdRegression extends TestCase {

    private DateTimeZone originalDefault;

    protected void setUp() throws Exception {
        super.setUp();
        originalDefault = DateTimeZone.getDefault();
    }

    protected void tearDown() throws Exception {
        DateTimeZone.setDefault(originalDefault);
        super.tearDown();
    }

    public void testForTimeZone_oldWET() {
        TimeZone juZone = TimeZone.getTimeZone("WET");
        DateTimeZone zone = DateTimeZone.forTimeZone(juZone);
        assertEquals("WET", zone.getID());
    }
}