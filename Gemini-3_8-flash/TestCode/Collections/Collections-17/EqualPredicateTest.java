package org.apache.commons.collections.functors;

import junit.framework.TestCase;
import org.apache.commons.collections.Predicate;

public class EqualPredicateTest extends TestCase {

    private static class AlwaysFalseEquals {
        @Override
        public boolean equals(Object obj) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    public void testObjectFactoryUsesEqualsForTest() {
        AlwaysFalseEquals obj = new AlwaysFalseEquals();
        Predicate<AlwaysFalseEquals> predicate = EqualPredicate.equalPredicate(obj);
        // The buggy version uses DefaultEquator which checks reference equality first (o1 == o2)
        // returning true, whereas standard EqualPredicate(object) should delegate directly to equals(),
        // returning false.
        assertFalse(predicate.evaluate(obj));
    }
}