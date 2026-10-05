package com.fasterxml.jackson.databind.module;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomEnumKeyDeserializer1441Test extends BaseMapTest
{
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = SubTypeEnum.class, name = "sub")
    })
    interface SuperTypeEnum {
    }

    enum SubTypeEnum implements SuperTypeEnum {
        FOO,
        BAR
    }

    public void testCustomEnumKeySerializerWithPolymorphic() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("test");
        module.addKeyDeserializer(SuperTypeEnum.class, new KeyDeserializer() {
            @Override
            public Object deserializeKey(String key, com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws IOException
            {
                return SubTypeEnum.valueOf(key);
            }
        });
        mapper.registerModule(module);

        TypeReference<Map<SuperTypeEnum, String>> typeRef =
            new TypeReference<Map<SuperTypeEnum, String>>() {};

        Map<SuperTypeEnum, String> result = mapper.readValue("{\"FOO\": \"val\"}", typeRef);
        assertNotNull(result);
        assertEquals("val", result.get(SubTypeEnum.FOO));
    }
}