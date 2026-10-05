package org.mockito.internal.stubbing.defaultanswers;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class GenericDeepStubsTypeBoundsTest {

    interface MultiBoundedType<K extends Comparable<K> & Cloneable> {
        K getBounded();
    }

    @Test
    public void should_create_mock_with_multiple_type_bounds() {
        MultiBoundedType<?> mock = mock(MultiBoundedType.class, RETURNS_DEEP_STUBS);

        Comparable<?> comparable = mock.getBounded();
        Cloneable cloneable = (Cloneable) mock.getBounded();

        assertNotNull(comparable);
        assertNotNull(cloneable);
    }
}