package org.apache.commons.collections4.map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MultiValueMapSerializationTest {

    private byte[] serialize(final Object obj) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    private Object deserialize(final byte[] bytes) throws Exception {
        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        final ObjectInputStream ois = new ObjectInputStream(bais);
        final Object result = ois.readObject();
        ois.close();
        return result;
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testUnsafeDeSerialization() throws Exception {
        final MultiValueMap map1 = MultiValueMap.multiValueMap(new HashMap(), ArrayList.class);
        final byte[] bytes1 = serialize(map1);
        final Object result1 = deserialize(bytes1);
        assertEquals(map1, result1);

        final MultiValueMap map2 = MultiValueMap.multiValueMap(new HashMap(), (Class) String.class);
        final byte[] bytes2 = serialize(map2);
        try {
            deserialize(bytes2);
            fail("unsafe clazz accepted when de-serializing MultiValueMap");
        } catch (final UnsupportedOperationException ex) {
            // expected
        }
    }
}