package org.mockito.internal.creation.instance;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class GeminiConstructorInstantiatorTest {

    static class OuterClass {
        class SomeInnerClass {
        }
    }

    static class ChildOfOuterClass extends OuterClass {
    }

    @Test
    public void creates_instances_of_inner_classes_with_outer_subclass_instance() {
        // When outer instance is a subclass of the inner class's declaring class,
        // ConstructorInstantiator should find the constructor accepting the declaring class
        // and instantiate the inner class successfully.
        ChildOfOuterClass outerChild = new ChildOfOuterClass();
        ConstructorInstantiator instantiator = new ConstructorInstantiator(outerChild);

        OuterClass.SomeInnerClass inner = instantiator.newInstance(OuterClass.SomeInnerClass.class);

        assertNotNull(inner);
        assertEquals(OuterClass.SomeInnerClass.class, inner.getClass());
    }
}