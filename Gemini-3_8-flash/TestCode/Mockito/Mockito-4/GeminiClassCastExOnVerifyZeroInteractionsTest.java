package org.mockitousage.bugs;

import org.junit.Test;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.internal.stubbing.answers.Returns;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GeminiClassCastExOnVerifyZeroInteractionsTest {

    interface TestMock {
        boolean m1();
    }

    @Test(expected = NoInteractionsWanted.class)
    public void should_not_throw_a_ClassCastException() {
        TestMock test = mock(TestMock.class, new Returns(false));
        test.m1();
        verifyZeroInteractions(test);
    }
}