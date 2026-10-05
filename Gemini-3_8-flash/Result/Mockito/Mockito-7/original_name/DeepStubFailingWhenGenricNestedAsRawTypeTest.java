package org.mockitousage.bugs.deepstubs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeepStubFailingWhenGenricNestedAsRawTypeTest {

    interface MyClass1<T> {
        MyClass2<T> getNested();
    }

    interface MyClass2<T> {
        MyClass3 getNested();
    }

    interface MyClass3<T> {
        String returnSomething();
    }

    @Test
    public void discoverDeepMockingOfGenerics() {
        MyClass1 myMock1 = mock(MyClass1.class, RETURNS_DEEP_STUBS);
        when(myMock1.getNested().getNested().returnSomething()).thenReturn("Hello World.");

        assertEquals("Hello World.", myMock1.getNested().getNested().returnSomething());
    }
}