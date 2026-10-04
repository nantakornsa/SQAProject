package com.example.algorithm2;

import java.util.Collections;

import org.mockito.internal.invocation.InvocationMatcher;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class Algorithm2Test {

    @FuzzTest(maxDuration = "1m")
    void testMockitoBug1(FuzzedDataProvider data) {
        try {

            InvocationMatcher matcher = new InvocationMatcher(null, Collections.emptyList());
            
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Crash Found: Triggered Mockito-1 Bug!", e);
            
        } catch (IllegalArgumentException | NullPointerException expected) {
            
        } catch (Exception e) {
        }
    }
}