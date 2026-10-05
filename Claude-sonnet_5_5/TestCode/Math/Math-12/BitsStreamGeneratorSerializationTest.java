package org.apache.commons.math3.random;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-927: the state of {@link BitsStreamGenerator}
 * (cached gaussian value) must be preserved by serialization.
 */
public class BitsStreamGeneratorSerializationTest {

    @Test
    public void testSerializationPreservesNextGaussian() throws Exception {
        final Well19937c original = new Well19937c(123456789L);

        // Generates two gaussian values, caching the second one.
        original.nextGaussian();

        // Serialize and deserialize.
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(original);
        oos.close();

        final ObjectInputStream ois =
            new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        final Well19937c copy = (Well19937c) ois.readObject();
        ois.close();

        Assert.assertTrue(original instanceof java.io.Serializable);
        Assert.assertTrue(BitsStreamGenerator.class.isAssignableFrom(copy.getClass()));
        Assert.assertTrue(java.io.Serializable.class.isAssignableFrom(BitsStreamGenerator.class));

        // The cached gaussian must be identical in both generators.
        Assert.assertEquals(original.nextGaussian(), copy.nextGaussian(), 0d);

        // And subsequent values must remain identical.
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(original.nextGaussian(), copy.nextGaussian(), 0d);
        }
    }
}