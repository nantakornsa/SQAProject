package org.apache.commons.lang3.math;

import org.junit.Test;

import static org.junit.Assert.fail;

public class NumberUtilsLang638Test {

    @Test
    public void testLang638() {
        try {
            NumberUtils.createNumber("1eE");
            fail("Expected NumberFormatException for input '1eE'");
        } catch (NumberFormatException expected) {
            // Expected
        }

        try {
            NumberUtils.createNumber("1.0eE");
            fail("Expected NumberFormatException for input '1.0eE'");
        } catch (NumberFormatException expected) {
            // Expected
        }
    }
}