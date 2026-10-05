package org.joda.time.format;

import junit.framework.TestCase;

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GJChronology;

/**
 * Regression test for parsing week-based dates where the month/week fields
 * must not cause the wrong default year to be applied.
 */
public class TestDateTimeParserBucketWeekYearRegression extends TestCase {

    private void checkWeekyear(int year, int expectedDay, int expectedMonth, int expectedYear) {
        Chronology chrono = GJChronology.getInstanceUTC();
        DateTimeFormatter f = DateTimeFormat.forPattern("xxxx-MM-ww").withChronology(chrono);
        assertEquals(new LocalDate(expectedYear, expectedMonth, expectedDay, chrono),
                f.parseLocalDate(year + "-01-01"));
    }

    private void checkYear(int year, int expectedDay, int expectedMonth, int expectedYear) {
        Chronology chrono = GJChronology.getInstanceUTC();
        DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-ww").withChronology(chrono);
        assertEquals(new LocalDate(expectedYear, expectedMonth, expectedDay, chrono),
                f.parseLocalDate(year + "-01-01"));
    }

    public void testParseLocalDate_weekyear_month_week_2010() {
        checkWeekyear(2010, 4, 1, 2010);
    }

    public void testParseLocalDate_weekyear_month_week_2011() {
        checkWeekyear(2011, 3, 1, 2011);
    }

    public void testParseLocalDate_weekyear_month_week_2012() {
        checkWeekyear(2012, 2, 1, 2012);
    }

    public void testParseLocalDate_year_month_week_2010() {
        checkYear(2010, 4, 1, 2010);
    }

    public void testParseLocalDate_year_month_week_2011() {
        checkYear(2011, 3, 1, 2011);
    }

    public void testParseLocalDate_year_month_week_2012() {
        checkYear(2012, 2, 1, 2012);
    }

    public void testParseLocalDate_year_month_week_2016() {
        checkYear(2016, 4, 1, 2016);
    }
}