package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for adjustOffset overlap handling (Time-17).
 */
public class TestDateTimeZoneAdjustOffsetRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestDateTimeZoneAdjustOffsetRegression.class);
    }

    public TestDateTimeZoneAdjustOffsetRegression(String name) {
        super(name);
    }

    public void testBug3476684_adjustOffset() {
        final DateTimeZone zone = DateTimeZone.forID("America/Sao_Paulo");
        DateTime base = new DateTime(2012, 2, 25, 22, 15, zone);
        DateTime baseBefore = base.plusHours(1);  // 23:15 (first)
        DateTime baseAfter = base.plusHours(2);  // 23:15 (second)

        assertSame(base, base.withEarlierOffsetAtOverlap());
        assertSame(base, base.withLaterOffsetAtOverlap());

        assertSame(baseBefore, baseBefore.withEarlierOffsetAtOverlap());
        assertEquals(baseAfter, baseBefore.withLaterOffsetAtOverlap());

        assertSame(baseAfter, baseAfter.withLaterOffsetAtOverlap());
        assertEquals(baseBefore, baseAfter.withEarlierOffsetAtOverlap());

        // explicit offset checks
        assertEquals("2012-02-25T23:15:00.000-02:00", baseBefore.toString());
        assertEquals("2012-02-25T23:15:00.000-03:00", baseAfter.toString());
        assertEquals("2012-02-25T23:15:00.000-03:00",
                baseBefore.withLaterOffsetAtOverlap().toString());
        assertEquals("2012-02-25T23:15:00.000-02:00",
                baseAfter.withEarlierOffsetAtOverlap().toString());
    }
}