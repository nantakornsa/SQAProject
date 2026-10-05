package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.util.List;

public class VisibilityFeaturesTest extends BaseMapTest {

    static class ClassWithSetterAndField {
        @JsonProperty("groupname")
        private String groupname;

        public void setName(String name) { }
    }

    public void testDisableAutoDetectSettersForSerialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.disable(MapperFeature.AUTO_DETECT_FIELDS);
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS);

        BeanDescription desc = mapper.getSerializationConfig()
                .introspect(mapper.constructType(ClassWithSetterAndField.class));
        List<BeanPropertyDefinition> props = desc.findProperties();

        assertEquals("Should find 1 property, not " + props.size() + "; properties = " + props,
                1, props.size());
        assertEquals("groupname", props.get(0).getName());
    }
}