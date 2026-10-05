package org.mockito.internal.stubbing.defaultanswers;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class ReturnsEmptyValuesIterableTest {

    @Test
    public void should_return_empty_iterable() {
        ReturnsEmptyValues values = new ReturnsEmptyValues();

        Object returnValue = values.returnValueFor(Iterable.class);

        assertNotNull("returnValueFor(Iterable.class) should not be null", returnValue);
        Iterable<?> iterable = (Iterable<?>) returnValue;
        assertFalse("Iterable should be empty", iterable.iterator().hasNext());
    }
}