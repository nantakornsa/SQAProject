package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for leap day handling in single field period factories
 * using partials (MonthDay).
 */
public class TestSingleFieldPeriodLeapDayRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestSingleFieldPeriodLeapDayRegression.class);
    }

    public TestSingleFieldPeriodLeapDayRegression(String name) {
        super(name);
    }

    public void testDaysBetween_RPartial_MonthDay_leapDay() {
        MonthDay start1 = new MonthDay(2, 1);
        MonthDay start2 = new MonthDay(2, 28);
        MonthDay end1 = new MonthDay(2, 28);
        MonthDay end2 = new MonthDay(2, 29);

        assertEquals(27, Days.daysBetween(start1, end1).getDays());
        assertEquals(28, Days.daysBetween(start1, end2).getDays());
        assertEquals(0, Days.daysBetween(start2, end1).getDays());
        assertEquals(1, Days.daysBetween(start2, end2).getDays());

        assertEquals(-27, Days.daysBetween(end1, start1).getDays());
        assertEquals(-28, Days.daysBetween(end2, start1).getDays());
        assertEquals(0, Days.daysBetween(end1, start2).getDays());
        assertEquals(-1, Days.daysBetween(end2, start2).getDays());
    }

    public void testMonthsBetween_RPartial_MonthDay_leapDay() {
        MonthDay start = new MonthDay(2, 29);
        MonthDay end = new MonthDay(3, 31);

        assertEquals(1, Months.monthsBetween(start, end).getMonths());
        assertEquals(-1, Months.monthsBetween(end, start).getMonths());
        assertEquals(0, Months.monthsBetween(start, start).getMonths());
    }
}