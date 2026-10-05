package org.jfree.data.time.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 * Regression test for bug 1864222 in {@link TimeSeries}.
 */
public class TimeSeriesBug1864222Test extends TestCase {

    /**
     * Returns the test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(TimeSeriesBug1864222Test.class);
    }

    /**
     * Constructs a new test case.
     *
     * @param name  the test name.
     */
    public TimeSeriesBug1864222Test(String name) {
        super(name);
    }

    /**
     * Tests creating a copy of a time series for a period that falls in a gap
     * between existing data items.
     */
    public void testBug1864222() {
        TimeSeries s = new TimeSeries("S");
        s.add(new Day(19, 1, 2008), 25.0);
        s.add(new Day(20, 1, 2008), 44.0);
        s.add(new Day(22, 1, 2008), 36.0);
        s.add(new Day(23, 1, 2008), 89.0);

        try {
            TimeSeries s2 = s.createCopy(new Day(21, 1, 2008), new Day(21, 1, 2008));
            assertEquals(0, s2.getItemCount());
        } catch (IllegalArgumentException e) {
            fail("createCopy should not throw IllegalArgumentException: " + e.getMessage());
        }
    }
}