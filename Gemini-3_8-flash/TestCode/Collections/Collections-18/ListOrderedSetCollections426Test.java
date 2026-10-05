package org.apache.commons.collections.set;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class ListOrderedSetCollections426Test extends TestCase {

    public void testRetainAllPerformance() {
        int size = 100000;
        ListOrderedSet<Integer> set = new ListOrderedSet<Integer>();
        for (int i = 0; i < size; i++) {
            set.add(Integer.valueOf(i));
        }

        List<Integer> list = new ArrayList<Integer>(size);
        for (int i = size; i < 2 * size; i++) {
            list.add(Integer.valueOf(i));
        }

        long start = System.currentTimeMillis();
        set.retainAll(list);
        long stop = System.currentTimeMillis();

        // On the buggy version, retainAll executes in O(N^2) time because it calls
        // collection.retainAll(coll) where coll is an ArrayList, taking tens of seconds or minutes.
        // On the fixed version, it completes in O(N) time, well under 5 seconds.
        assertTrue("retainAll took too long: " + (stop - start) + "ms", (stop - start) < 5000);
        assertTrue(set.isEmpty());
    }
}