package org.apache.commons.collections.map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import junit.framework.TestCase;

public class MultiValueMapSerializationTest extends TestCase {

    public void testSerialization() throws Exception {
        MultiValueMap map = new MultiValueMap();
        assertTrue("MultiValueMap must implement Serializable", map instanceof Serializable);

        map.put("key1", "value1");
        map.put("key1", "value2");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(map);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        MultiValueMap deserialized = (MultiValueMap) ois.readObject();
        ois.close();

        assertEquals(map.size(), deserialized.size());
        Collection values = (Collection) deserialized.get("key1");
        assertNotNull(values);
        assertTrue(values.containsAll(Arrays.asList(new String[]{"value1", "value2"})));
    }
}