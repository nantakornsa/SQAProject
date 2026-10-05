package org.mockitousage.bugs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GeminiInjectMocksShouldTryPropertySettersFirstBeforeFieldAccessTest {

    @Mock
    private Dependency dependency;

    @InjectMocks
    private AwaitingInjection awaitingInjection = new AwaitingInjection();

    @Test
    public void shouldInjectUsingPropertySetterIfAvailable() {
        assertTrue("Property setter should have been used for injection", awaitingInjection.propertySetterUsed);
    }

    static class Dependency {
    }

    static class AwaitingInjection {
        private Dependency dependency;
        private boolean propertySetterUsed = false;

        public void setDependency(Dependency dependency) {
            this.dependency = dependency;
            this.propertySetterUsed = true;
        }

        public Dependency getDependency() {
            return dependency;
        }
    }
}