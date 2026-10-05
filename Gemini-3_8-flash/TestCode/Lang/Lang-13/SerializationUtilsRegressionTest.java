package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SerializationUtilsRegressionTest {

    @Test
    public void testPrimitiveTypeClassSerialization() {
        Class<?>[] primitiveTypes = {
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            boolean.class,
            char.class,
            void.class
        };

        for (Class<?> primitiveType : primitiveTypes) {
            Class<?> clone = SerializationUtils.clone(primitiveType);
            assertEquals(primitiveType, clone);
        }
    }
}