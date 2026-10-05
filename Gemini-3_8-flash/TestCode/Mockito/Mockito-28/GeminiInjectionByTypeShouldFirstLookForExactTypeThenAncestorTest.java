package org.mockitousage.bugs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class GeminiInjectionByTypeShouldFirstLookForExactTypeThenAncestorTest {

    private static final Object REFERENCE = new Object();

    @Mock
    private Bean mockedBean;

    @InjectMocks
    private IllegalInjectionExample illegalInjectionExample = new IllegalInjectionExample();

    @Test
    public void mock_should_be_injected_once_and_in_the_best_matching_type() {
        assertSame(REFERENCE, illegalInjectionExample.mockShouldNotGoInHere);
        assertSame(mockedBean, illegalInjectionExample.mockShouldGoInHere);
    }

    public static class Bean {}

    public static class IllegalInjectionExample {
        protected Object mockShouldNotGoInHere = REFERENCE;
        protected Bean mockShouldGoInHere;
    }
}