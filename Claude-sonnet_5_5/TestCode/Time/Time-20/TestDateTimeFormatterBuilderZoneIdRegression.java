package org.joda.time.format;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Regression test for zone id parsing when one id is a prefix of another
 * (e.g. America/Dawson and America/Dawson_Creek).
 */
public class TestDateTimeFormatterBuilderZoneIdRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestDateTimeFormatterBuilderZoneIdRegression.class);
    }

    public TestDateTimeFormatterBuilderZoneIdRegression(String name) {
        super(name);
    }

    public void test_printParseZoneDawsonCreek() {
        DateTimeFormatterBuilder bld = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm ").appendTimeZoneId();
        DateTimeFormatter f = bld.toFormatter();

        DateTime dt = new DateTime(2007, 3, 4, 12, 30, 0, DateTimeZone.forID("America/Dawson_Creek"));
        assertEquals("2007-03-04 12:30 America/Dawson_Creek", f.print(dt));
        assertEquals(dt, f.parseDateTime("2007-03-04 12:30 America/Dawson_Creek"));
    }

    public void test_printParseZoneDawson() {
        DateTimeFormatterBuilder bld = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm ").appendTimeZoneId();
        DateTimeFormatter f = bld.toFormatter();

        DateTime dt = new DateTime(2007, 3, 4, 12, 30, 0, DateTimeZone.forID("America/Dawson"));
        assertEquals("2007-03-04 12:30 America/Dawson", f.print(dt));
        assertEquals(dt, f.parseDateTime("2007-03-04 12:30 America/Dawson"));
    }
}