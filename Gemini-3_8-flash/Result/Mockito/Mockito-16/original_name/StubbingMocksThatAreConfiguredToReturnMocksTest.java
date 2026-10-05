package org.mockitousage.bugs;

import org.junit.Test;
import org.mockitoutil.TestBase;

import static org.mockito.Mockito.*;

public class StubbingMocksThatAreConfiguredToReturnMocksTest extends TestBase {

    interface IMethods {
        IMethods objectReturningMethodNoArgs();
    }

    @Test
    public void shouldAllowStubbingMocksConfiguredWithRETURNS_MOCKS() {
        IMethods mock = mock(IMethods.class, RETURNS_MOCKS);
        when(mock.objectReturningMethodNoArgs()).thenReturn(null);
    }
}