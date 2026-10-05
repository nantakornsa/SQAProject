package com.fasterxml.jackson.core;

import org.junit.Assert;
import org.junit.Test;

public class JsonPointerLeadingZeroTest {

    @Test
    public void testLeadingZeroIndexNotAllowed() {
        JsonPointer ptr1 = JsonPointer.compile("/00");
        Assert.assertEquals(-1, ptr1.getMatchingIndex());

        JsonPointer ptr2 = JsonPointer.compile("/01");
        Assert.assertEquals(-1, ptr2.getMatchingIndex());

        // Single zero is valid
        JsonPointer ptr3 = JsonPointer.compile("/0");
        Assert.assertEquals(0, ptr3.getMatchingIndex());
    }
}