package org.apache.commons.math.linear;

import junit.framework.TestCase;

import org.apache.commons.math.exception.NumberIsTooLargeException;

public class OpenMapRealMatrixTest extends TestCase {

    public void testMath679() {
        try {
            new OpenMapRealMatrix(3, Integer.MAX_VALUE);
            fail("Expected NumberIsTooLargeException");
        } catch (NumberIsTooLargeException e) {
            // expected
        }
    }

    public void testMath679LargeBothDimensions() {
        try {
            new OpenMapRealMatrix(Integer.MAX_VALUE, Integer.MAX_VALUE);
            fail("Expected NumberIsTooLargeException");
        } catch (NumberIsTooLargeException e) {
            // expected
        }
    }
}