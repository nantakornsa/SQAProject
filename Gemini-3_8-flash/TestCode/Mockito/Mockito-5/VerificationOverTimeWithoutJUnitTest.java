package org.mockitointegration;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.cglib.proxy.Enhancer;
import org.mockito.internal.verification.VerificationOverTimeImpl;
import org.mockitoutil.ClassLoaders;
import org.objenesis.Objenesis;

public class VerificationOverTimeWithoutJUnitTest {

    @Test
    public void verification_over_time_should_not_depend_on_junit() throws Exception {
        ClassLoader classLoaderWithoutJUnit = ClassLoaders.excludingClassLoader()
                .withCodeSourceUrlOf(
                        Mockito.class,
                        Matcher.class,
                        Enhancer.class,
                        Objenesis.class
                )
                .without("junit", "org.junit")
                .build();

        Class.forName(
                "org.mockito.internal.verification.VerificationOverTimeImpl",
                true,
                classLoaderWithoutJUnit
        );
    }
}