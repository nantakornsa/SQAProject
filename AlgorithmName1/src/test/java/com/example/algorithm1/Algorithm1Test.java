package com.example.algorithm1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Algorithm1Test {

    @Test
    void testRunSumsInput() {
        Algorithm1 algo = new Algorithm1();
        int result = algo.run(new int[]{1, 2, 3});
        assertEquals(6, result);
    }
}
