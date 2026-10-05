package org.apache.commons.collections.list;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class Collections304Test extends TestCase {

    public void testCollections304() {
        List list = new ArrayList();
        SetUniqueList decoratedList = SetUniqueList.decorate(list);
        String s1 = "Apple";
        String s2 = "Lemon";
        String s3 = "Orange";
        String s4 = "Strawberry";

        decoratedList.add(s1);
        decoratedList.add(s2);
        decoratedList.add(s3);

        assertEquals(3, decoratedList.size());

        decoratedList.set(1, s4);

        assertEquals(3, decoratedList.size());

        decoratedList.add(1, s4);

        assertEquals(3, decoratedList.size());

        decoratedList.add(1, s2);

        assertEquals(4, decoratedList.size());
    }
}