package org.mockito.internal.invocation;

import org.junit.Test;
import org.mockito.internal.matchers.AnyVararg;
import org.mockito.internal.matchers.LocalizedMatcher;
import org.mockitoutil.TestBase;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class InvocationMatcherTest extends TestBase {

    interface IMethods {
        void varargs(String... args);
    }

    @Test
    public void shouldMatchCaptureArgumentsWhenArgsCountDoesNOTMatch() throws Exception {
        // given
        Method method = IMethods.class.getMethod("varargs", String[].class);
        Invocation invocation = new Invocation(
                new Object(),
                new SerializableMethod(method),
                new Object[0],
                1,
                null
        );

        // when
        @SuppressWarnings({"rawtypes", "unchecked"})
        List matchers = Arrays.asList(new LocalizedMatcher(AnyVararg.ANY_VARARG));
        InvocationMatcher invocationMatcher = new InvocationMatcher(invocation, matchers);

        // then
        invocationMatcher.captureArgumentsFrom(invocation);
    }
}