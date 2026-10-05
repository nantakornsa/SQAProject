package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RandomStringUtilsLang805Test {

    @Test
    public void testLANG805() {
        // Calling random with specified chars and default start/end (start == 0 && end == 0)
        // In buggy version, end is set to Integer.MAX_VALUE instead of chars.length,
        // causing ArrayIndexOutOfBoundsException when indexing chars array.
        char[] chars = new char[]{'a', 'b', 'c'};
        String result = RandomStringUtils.random(10, chars);
        assertEquals(10, result.length());
        for (char c : result.toCharArray()) {
            if (c != 'a' && c != 'b' && c != 'c') {
                fail("Generated character '" + c + "' not in provided chars array");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyCharArrayThrowsIllegalArgumentException() {
        // In buggy version, empty char array does not throw IllegalArgumentException at the beginning
        RandomStringUtils.random(1, new char[0]);
    }
}