package com.example.algorithm2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Algorithm2Test {

    @Test
    void testRunMultipliesInput() {
        Algorithm2 algo = new Algorithm2();
        int result = algo.run(new int[]{1, 2, 3});
        assertEquals(6, result);
    }
}
