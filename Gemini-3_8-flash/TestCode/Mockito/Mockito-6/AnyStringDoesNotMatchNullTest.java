package org.mockitousage.matchers;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnyStringDoesNotMatchNullTest {

    interface TestInterface {
        String testMethod(String arg);
    }

    @Test
    public void shouldNotMatchNullWhenUsingAnyStringMatcher() {
        TestInterface mock = mock(TestInterface.class);

        when(mock.testMethod(anyString())).thenReturn("matched");

        assertNull(mock.testMethod(null));
    }
}