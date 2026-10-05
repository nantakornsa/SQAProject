package com.fasterxml.jackson.databind.jsontype;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class ProblemHandlerUnknownTypeId2221Test extends BaseMapTest
{
    static class GenericContent {
        private List<InnerObject> innerObjects;

        public List<InnerObject> getInnerObjects() {
            return innerObjects;
        }

        public void setInnerObjects(List<InnerObject> innerObjects) {
            this.innerObjects = innerObjects;
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    static class InnerObject {
        public String name;
    }

    static class KnownInnerObject extends InnerObject {
    }

    private static final String JSON = "{\n" +
            "  \"innerObjects\": [\n" +
            "    {\n" +
            "      \"type\": \"KnownInnerObject\",\n" +
            "      \"name\": \"Known\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"UnknownInnerObject\",\n" +
            "      \"name\": \"Unknown\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public void testWithDeserializationProblemHandlerReturningNull() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(KnownInnerObject.class);
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public JavaType handleUnknownTypeId(DeserializationContext ctxt, JavaType baseType,
                    String subTypeId, TypeIdResolver idResolver, String failureMsg) throws IOException {
                return null;
            }
        });

        GenericContent result = mapper.readValue(JSON, GenericContent.class);
        assertNotNull(result);
        assertNotNull(result.getInnerObjects());
        assertEquals(2, result.getInnerObjects().size());
        assertNotNull(result.getInnerObjects().get(0));
        assertNull(result.getInnerObjects().get(1));
    }
}