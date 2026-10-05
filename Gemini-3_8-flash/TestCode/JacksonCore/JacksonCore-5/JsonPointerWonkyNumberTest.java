package com.fasterxml.jackson.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class JsonPointerWonkyNumberTest {

    @Test
    public void testWonkyNumber173() {
        JsonPointer ptr = JsonPointer.compile("/1e0");
        assertNotNull(ptr);
        assertEquals(-1, ptr.getMatchingIndex());
    }
}