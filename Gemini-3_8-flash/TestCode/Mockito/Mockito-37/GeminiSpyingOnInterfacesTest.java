package org.mockitousage.spies;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.exceptions.base.MockitoException;

public class GeminiSpyingOnInterfacesTest {

    interface TestInterface {
        void simpleMethod();
    }

    @Test
    public void shouldFailFastWhenCallingRealMethodOnInterface() {
        TestInterface mock = mock(TestInterface.class);
        try {
            when(mock.simpleMethod()).thenCallRealMethod();
            fail("Expected MockitoException when calling real method on interface");
        } catch (MockitoException e) {
            // expected
        }
    }
}