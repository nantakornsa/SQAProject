package org.mockito.internal.util.reflection;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class GenericsSelfReferenceTest {

    interface GenericsSelfReference<T extends GenericsSelfReference<T>> {
        T self();
    }

    @Test
    public void typeVariable_of_self_type() throws Exception {
        Method method = GenericsSelfReference.class.getMethod("self");
        GenericMetadataSupport genericMetadata = GenericMetadataSupport.inferFrom(GenericsSelfReference.class).resolveGenericReturnType(method);

        assertEquals(GenericsSelfReference.class, genericMetadata.rawType());
    }
}