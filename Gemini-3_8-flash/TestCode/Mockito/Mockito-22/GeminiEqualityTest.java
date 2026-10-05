package org.mockito.internal.matchers;

import org.junit.Test;
import org.mockitoutil.TestBase;

import static org.mockito.internal.matchers.Equality.areEqual;

public class GeminiEqualityTest extends TestBase {

    static class BadEquals {
        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("equals should not be called when objects are identical");
        }
    }

    @Test
    public void shouldReturnTrueWhenComparingIdenticalObjectsEvenIfEqualsThrowsException() {
        Object badEquals = new BadEquals();
        assertTrue(areEqual(badEquals, badEquals));
    }
}