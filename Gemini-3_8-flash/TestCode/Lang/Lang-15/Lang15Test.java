package org.apache.commons.lang3.reflect;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class Lang15Test {

    public interface This<K, V> {
    }

    public static class Other<T> implements This<String, T> {
    }

    @Test
    public void testGetTypeArgumentsWithTypeParametersOnSubclass() {
        Map<TypeVariable<?>, Type> typeVarAssigns = TypeUtils.getTypeArguments(Other.class, This.class);
        Assert.assertEquals(2, typeVarAssigns.size());
        Assert.assertEquals(String.class, typeVarAssigns.get(This.class.getTypeParameters()[0]));
        Assert.assertEquals(Other.class.getTypeParameters()[0], typeVarAssigns.get(This.class.getTypeParameters()[1]));
    }
}