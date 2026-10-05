package org.apache.commons.collections4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

public class IteratorUtilsCollatedIteratorTest {

    @Test
    public void testCollatedIteratorWithNullComparator() {
        List<Integer> list1 = Arrays.asList(1, 3, 5);
        List<Integer> list2 = Arrays.asList(2, 4, 6);

        // When comparator is null, natural ordering should be used.
        // In the buggy version, CollatingIterator throws NullPointerException because comparator is null.
        Iterator<Integer> it = IteratorUtils.collatedIterator(null, list1.iterator(), list2.iterator());

        List<Integer> result = IteratorUtils.toList(it);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), result);
    }
}