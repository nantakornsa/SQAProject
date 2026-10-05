package org.apache.commons.lang3.builder;

import junit.framework.TestCase;

public class HashCodeBuilderRegistryTest extends TestCase {

    static class SimpleCycleObject {
        SimpleCycleObject cycle;

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

    public void testRegistryCleanedUpAfterReflectionHashCode() {
        SimpleCycleObject obj = new SimpleCycleObject();
        obj.cycle = obj;

        obj.hashCode();

        assertNull("Registry should be null after reflectionHashCode completes", HashCodeBuilder.getRegistry());
    }
}