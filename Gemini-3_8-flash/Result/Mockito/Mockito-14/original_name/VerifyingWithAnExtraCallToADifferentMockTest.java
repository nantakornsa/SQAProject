package org.mockitousage.bugs;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

public class VerifyingWithAnExtraCallToADifferentMockTest extends TestBase {

    @Test
    public void shouldAllowVerifyingWhenOtherMockCallIsInTheSameLine() {
        IMethods mock = mock(IMethods.class);
        IMethods mockTwo = mock(IMethods.class);

        // given
        when(mock.otherMethod()).thenReturn("foo");

        // when
        mockTwo.simpleMethod("foo");

        // then
        verify(mockTwo).simpleMethod(mock.otherMethod());
    }
}