package org.mockitousage.bugs;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockitoutil.TestBase;

import java.util.Iterator;

public class InheritedGenericsPolymorphicCallTest extends TestBase {

    interface MyIterable<T> extends Iterable<T> {
        Iterator<T> iterator();
    }

    @Test
    public void shouldStubbingWorkWhenInvokedViaSuperType() {
        MyIterable<String> iterable = Mockito.mock(MyIterable.class);
        Iterator<String> myIterator = Mockito.mock(Iterator.class);

        Mockito.when(iterable.iterator()).thenReturn(myIterator);

        Assert.assertNotNull(((Iterable<String>) iterable).iterator());
        Assert.assertNotNull(iterable.iterator());
    }

    @Test
    public void shouldVerificationWorkWhenInvokedViaSuperType() {
        MyIterable<String> iterable = Mockito.mock(MyIterable.class);

        ((Iterable<String>) iterable).iterator();

        Mockito.verify(iterable).iterator();
    }
}