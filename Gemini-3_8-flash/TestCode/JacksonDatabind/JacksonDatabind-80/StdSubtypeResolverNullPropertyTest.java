package com.fasterxml.jackson.databind.jsontype;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;

public class StdSubtypeResolverNullPropertyTest extends BaseMapTest
{
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = SubType.class, name = "sub")
    })
    static class BaseType { }

    @JsonTypeName("sub")
    static class SubType extends BaseType { }

    public void testCollectSubtypesWithNullProperty() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        SubtypeResolver resolver = new StdSubtypeResolver();
        JavaType baseType = mapper.constructType(BaseType.class);

        // Both collectAndResolveSubtypesByClass and collectAndResolveSubtypesByTypeId
        // should safely handle a null AnnotatedMember property
        Collection<NamedType> byClass = resolver.collectAndResolveSubtypesByClass(
                mapper.getDeserializationConfig(), null, baseType);
        assertNotNull(byClass);
        assertEquals(2, byClass.size());

        Collection<NamedType> byTypeId = resolver.collectAndResolveSubtypesByTypeId(
                mapper.getDeserializationConfig(), null, baseType);
        assertNotNull(byTypeId);
        assertEquals(2, byTypeId.size());
    }
}