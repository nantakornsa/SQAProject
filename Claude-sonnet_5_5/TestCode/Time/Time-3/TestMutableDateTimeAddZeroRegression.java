package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test: adding zero to a MutableDateTime must not change the
 * instant, even during a DST overlap.
 */
public class TestMutableDateTimeAddZeroRegression extends TestCase {

    private static final DateTimeZone BERLIN = DateTimeZone.forID("Europe/Berlin");
    private static final String EXPECTED = "2011-10-30T02:30:00.000+01:00";

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestMutableDateTimeAddZeroRegression.class);
    }

    public TestMutableDateTimeAddZeroRegression(String name) {
        super(name);
    }

    private MutableDateTime createOverlapSecondOccurrence() {
        MutableDateTime test = new MutableDateTime(2011, 10, 30, 2, 30, 0, 0, BERLIN);
        test.addHours(1);
        assertEquals(EXPECTED, test.toString());
        return test;
    }

    public void testAddYears_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.addYears(0);
        assertEquals(EXPECTED, test.toString());
    }

    public void testAddMonths_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.addMonths(0);
        assertEquals(EXPECTED, test.toString());
    }

    public void testAddWeeks_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.addWeeks(0);
        assertEquals(EXPECTED, test.toString());
    }

    public void testAddDays_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.addDays(0);
        assertEquals(EXPECTED, test.toString());
    }

    public void testAddWeekyears_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.addWeekyears(0);
        assertEquals(EXPECTED, test.toString());
    }

    public void testAddDurationFieldType_addZero_dstOverlapWinter() {
        MutableDateTime test = createOverlapSecondOccurrence();
        test.add(DurationFieldType.days(), 0);
        assertEquals(EXPECTED, test.toString());
    }
}