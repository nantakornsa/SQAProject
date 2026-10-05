package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MixinInheritance515Test extends BaseMapTest
{
    public interface Person {
        String getName();
        String getCity();
    }

    public static class PersonImpl implements Person {
        @Override
        public String getName() { return "Bob"; }

        @Override
        public String getCity() { return "Seattle"; }
    }

    public interface PersonMixin extends Person {
        @Override
        @JsonProperty("name")
        String getName();
    }

    public interface PersonMixin2 extends PersonMixin {
        @Override
        @JsonProperty("city")
        String getCity();
    }

    public void testDisappearingMixins515() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixInAnnotations(Person.class, PersonMixin2.class);

        String json = mapper.writeValueAsString(new PersonImpl());
        assertEquals("{\"name\":\"Bob\",\"city\":\"Seattle\"}", json);
    }
}