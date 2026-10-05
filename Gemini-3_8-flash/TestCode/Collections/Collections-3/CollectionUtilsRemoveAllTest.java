package org.apache.commons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

public class CollectionUtilsRemoveAllTest extends TestCase {

    public CollectionUtilsRemoveAllTest(String testName) {
        super(testName);
    }

    public void testRemoveAll() {
        List base = new ArrayList();
        base.add("A");
        base.add("B");
        base.add("C");

        List sub = new ArrayList();
        sub.add("A");
        sub.add("C");
        sub.add("X");

        Collection result = CollectionUtils.removeAll(base, sub);
        assertEquals(1, result.size());
        assertFalse(result.contains("A"));
        assertTrue(result.contains("B"));
        assertFalse(result.contains("C"));
    }
}