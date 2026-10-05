package org.mockitousage.basicapi;

import org.junit.Test;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class SerializationWithExtraInterfacesTest extends TestBase {

    @Test
    public void shouldBeSerializableAndHaveExtraInterfaces() throws Exception {
        IMethods mock = mock(IMethods.class, withSettings().serializable().extraInterfaces(List.class));
        IMethods mockTwo = mock(IMethods.class, withSettings().extraInterfaces(List.class).serializable());

        serializeAndBack(mock);
        serializeAndBack(mockTwo);
    }

    @SuppressWarnings("unchecked")
    private <T> T serializeAndBack(T obj) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        new ObjectOutputStream(os).writeObject(obj);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        return (T) new ObjectInputStream(is).readObject();
    }
}