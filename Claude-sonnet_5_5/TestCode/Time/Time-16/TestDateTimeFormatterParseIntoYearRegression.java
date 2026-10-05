package org.joda.time.format;

import java.util.Locale;

import junit.framework.TestCase;

import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.ISOChronology;

/**
 * Regression test: parseInto must use the year of the supplied instant
 * as the default year rather than the formatter's default year.
 */
public class TestDateTimeFormatterParseIntoYearRegression extends TestCase {

    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone TOKYO = DateTimeZone.forID("Asia/Tokyo");

    public TestDateTimeFormatterParseIntoYearRegression(String name) {
        super(name);
    }

    public void testParseInto_monthOnly_baseStartYear() {
        DateTimeFormatter f = DateTimeFormat.forPattern("M").withLocale(Locale.UK);
        MutableDateTime result = new MutableDateTime(2004, 1, 1, 12, 20, 30, 0, TOKYO);
        assertEquals(1, f.parseInto(result, "5", 0));
        assertEquals(new MutableDateTime(2004, 5, 1, 12, 20, 30, 0, TOKYO), result);
    }

    public void testParseInto_monthDay_feb29() {
        DateTimeFormatter f = DateTimeFormat.forPattern("M-d").withChronology(ISOChronology.getInstanceUTC());
        MutableDateTime result = new MutableDateTime(2004, 1, 1, 12, 20, 30, 0, UTC);
        assertEquals(4, f.parseInto(result, "2-29", 0));
        assertEquals(new MutableDateTime(2004, 2, 29, 12, 20, 30, 0, UTC), result);
    }
}