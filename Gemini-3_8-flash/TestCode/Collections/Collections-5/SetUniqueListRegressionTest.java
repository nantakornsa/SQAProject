package org.apache.commons.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-249: SetUniqueList.addAll(int, Collection)
 * appends elements instead of inserting at the specified index.
 */
public class SetUniqueListRegressionTest extends TestCase {

    public void testAddAllAtIndex() {
        List list = SetUniqueList.decorate(new ArrayList(), new HashSet());
        Integer existingElement = new Integer(1);
        list.add(existingElement);

        Integer firstNewElement = new Integer(2);
        Integer secondNewElement = new Integer(3);
        List collection = Arrays.asList(new Integer[] {firstNewElement, secondNewElement});

        list.addAll(0, collection);

        assertEquals("Size should be 3 after adding two elements", 3, list.size());
        assertEquals("First new element should be at index 0", firstNewElement, list.get(0));
        assertEquals("Second new element should be at index 1", secondNewElement, list.get(1));
        assertEquals("Existing element should shift to index 2", existingElement, list.get(2));
    }
}