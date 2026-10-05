package org.apache.commons.lang3;

import junit.framework.TestCase;

/**
 * Regression test for LANG-571 in {@link ArrayUtils}.
 */
public class ArrayUtilsAddRegressionTest extends TestCase {

    public void testAddBothNullArgumentsThrowsIllegalArgumentException() {
        String[] stringArray = null;
        String aString = null;
        try {
            ArrayUtils.add(stringArray, aString);
            fail("Should have caused IllegalArgumentException when both array and element are null");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        try {
            ArrayUtils.add(stringArray, 0, aString);
            fail("Should have caused IllegalArgumentException when both array and element are null");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}