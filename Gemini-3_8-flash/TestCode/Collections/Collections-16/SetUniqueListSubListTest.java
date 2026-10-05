package org.apache.commons.collections.list;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-307:
 * SetUniqueList.subList should create a new set containing only the elements
 * of the subList rather than sharing the parent list's set.
 */
public class SetUniqueListSubListTest extends TestCase {

    public SetUniqueListSubListTest(String name) {
        super(name);
    }

    public void testSubListDoesNotContainExcludedElements() {
        List list = new ArrayList();
        List uniqueList = SetUniqueList.decorate(list);

        String hello = "Hello";
        String world = "World";
        uniqueList.add(hello);
        uniqueList.add(world);

        List subUniqueList = uniqueList.subList(0, 1);

        assertTrue(subUniqueList.contains(hello));
        // On buggy version, subUniqueList shares the parent set, so contains("World") returns true
        assertFalse("subList should not contain element outside its range", subUniqueList.contains(world));
        assertEquals(1, subUniqueList.size());
    }

    public void testEmptySubListContainsNoElements() {
        List list = new ArrayList();
        List uniqueList = SetUniqueList.decorate(list);

        String hello = "Hello";
        String world = "World";
        uniqueList.add(hello);
        uniqueList.add(world);

        List subUniqueList = uniqueList.subList(0, 0);

        assertFalse("empty subList should not contain elements", subUniqueList.contains(hello));
        assertFalse("empty subList should not contain elements", subUniqueList.contains(world));
        assertEquals(0, subUniqueList.size());
    }
}