package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.joda.time.field.UnsupportedDurationField;

/**
 * Regression test for Partial.with() when base and argument have no range.
 */
public class TestPartialNoRangeRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestPartialNoRangeRegression.class);
    }

    public TestPartialNoRangeRegression(String name) {
        super(name);
    }

    public void testWith_baseAndArgHaveNoRange() {
        Partial test = new Partial(DateTimeFieldType.year(), 1);
        Partial result = test.with(DateTimeFieldType.era(), 1);
        assertEquals(2, result.size());
        assertEquals(0, result.indexOf(DateTimeFieldType.era()));
        assertEquals(1, result.indexOf(DateTimeFieldType.year()));
    }

    public void testUnsupportedDurationFieldCompareTo() {
        DurationField unsupported = UnsupportedDurationField.getInstance(DurationFieldType.eras());
        DurationField supported = DurationFieldType.years().getField(
                org.joda.time.chrono.ISOChronology.getInstanceUTC());
        assertTrue(unsupported.compareTo(supported) > 0);
        assertEquals(0, unsupported.compareTo(unsupported));
    }
}