package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestPeriodNormalizedMonthsRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestPeriodNormalizedMonthsRegression.class);
    }

    public void testNormalizedStandard_periodType_months_yearsFoldedIntoMonths() {
        Period test = new Period(1, 15, 0, 0, 0, 0, 0, 0);
        Period result = test.normalizedStandard(PeriodType.months());
        assertEquals(new Period(1, 15, 0, 0, 0, 0, 0, 0), test);
        assertEquals(new Period(0, 27, 0, 0, 0, 0, 0, 0, PeriodType.months()), result);
        assertEquals(27, result.getMonths());
    }

    public void testNormalizedStandard_periodType_months_onlyMonths() {
        Period test = new Period(0, 27, 0, 0, 0, 0, 0, 0);
        Period result = test.normalizedStandard(PeriodType.months());
        assertEquals(new Period(0, 27, 0, 0, 0, 0, 0, 0, PeriodType.months()), result);
    }

    public void testNormalizedStandard_periodType_yearMonthDay_stillNormalizes() {
        Period test = new Period(1, 15, 0, 0, 0, 0, 0, 0);
        Period result = test.normalizedStandard(PeriodType.yearMonthDay());
        assertEquals(2, result.getYears());
        assertEquals(3, result.getMonths());
    }
}