package org.joda.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Regression test for unsupported duration field ordering in Partial.
 */
public class TestPartial_UnsupportedFieldOrderRegression extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestPartial_UnsupportedFieldOrderRegression.class);
    }

    public TestPartial_UnsupportedFieldOrderRegression(String name) {
        super(name);
    }

    private void assertMessageContains(Throwable ex, String... texts) {
        String msg = ex.getMessage();
        assertNotNull(msg);
        for (String text : texts) {
            assertTrue("Message '" + msg + "' should contain '" + text + "'", msg.contains(text));
        }
    }

    public void testConstructor_eraAfterYearNotInOrder() throws Throwable {
        int[] values = new int[] {1, 1, 1};
        DateTimeFieldType[] types = new DateTimeFieldType[] {
            DateTimeFieldType.year(), DateTimeFieldType.era(), DateTimeFieldType.monthOfYear() };
        try {
            new Partial(types, values);
            fail();
        } catch (IllegalArgumentException ex) {
            assertMessageContains(ex, "must be in order", "largest-smallest");
        }
    }

    public void testConstructor_eraAtEndNotInOrder() throws Throwable {
        int[] values = new int[] {1, 1, 1};
        DateTimeFieldType[] types = new DateTimeFieldType[] {
            DateTimeFieldType.year(), DateTimeFieldType.dayOfMonth(), DateTimeFieldType.era() };
        try {
            new Partial(types, values);
            fail();
        } catch (IllegalArgumentException ex) {
            assertMessageContains(ex, "must be in order", "largest-smallest");
        }
    }
}