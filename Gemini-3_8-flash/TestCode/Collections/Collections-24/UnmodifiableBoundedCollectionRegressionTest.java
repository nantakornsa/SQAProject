package org.apache.commons.collections4.collection;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.collections4.BoundedCollection;
import org.apache.commons.collections4.Unmodifiable;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

/**
 * Regression test for COLLECTIONS-496: UnmodifiableBoundedCollection should implement
 * Unmodifiable and return the same collection instance when decorating an already unmodifiable bounded collection.
 */
public class UnmodifiableBoundedCollectionRegressionTest {

    @Test
    public void testUnmodifiableBoundedCollection() {
        final BoundedCollection<String> boundedQueue = new CircularFifoQueue<String>(5);
        final BoundedCollection<String> unmodifiableBoundedQueue =
                UnmodifiableBoundedCollection.unmodifiableBoundedCollection(boundedQueue);

        assertTrue("UnmodifiableBoundedCollection should implement Unmodifiable",
                unmodifiableBoundedQueue instanceof Unmodifiable);

        assertSame("Decorating an already unmodifiable bounded collection should return the same instance",
                unmodifiableBoundedQueue,
                UnmodifiableBoundedCollection.unmodifiableBoundedCollection(unmodifiableBoundedQueue));
    }
}