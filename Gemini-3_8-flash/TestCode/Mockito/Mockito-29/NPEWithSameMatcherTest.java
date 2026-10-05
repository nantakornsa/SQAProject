package org.mockitousage.bugs;

import org.junit.Test;
import org.mockito.Mock;
import org.mockitoutil.TestBase;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

public class NPEWithSameMatcherTest extends TestBase {

    interface IMethods {
        void objectArgMethod(Object arg);
    }

    @Mock
    private IMethods mock;

    @Test(expected = AssertionError.class)
    public void shouldNotThrowNPEWhenNullPassedToSame() {
        mock.objectArgMethod("not null");

        verify(mock).objectArgMethod(same(null));
    }
}