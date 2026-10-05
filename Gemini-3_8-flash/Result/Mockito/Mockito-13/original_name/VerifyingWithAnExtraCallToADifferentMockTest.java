package org.mockitousage.bugs;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

import static org.mockito.Mockito.*;

public class VerifyingWithAnExtraCallToADifferentMockTest extends TestBase {

    @Mock
    private IMethods mock;
    @Mock
    private IMethods mockTwo;

    @Test
    public void shouldAllowVerifyingWhenOtherMockCallIsInTheSameLine() {
        //given
        when(mock.otherMethod()).thenReturn("foo");

        //when
        mockTwo.simpleMethod("foo");

        //then
        verify(mockTwo).simpleMethod(mock.otherMethod());
        try {
            verify(mockTwo, never()).simpleMethod(mock.otherMethod());
            fail();
        } catch (NeverWantedButInvoked e) {
            // expected
        }
    }
}