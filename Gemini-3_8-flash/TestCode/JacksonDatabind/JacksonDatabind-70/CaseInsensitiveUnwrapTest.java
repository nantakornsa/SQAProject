package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CaseInsensitiveUnwrapTest extends BaseMapTest {

    static class Address {
        public String street;
        public String city;
    }

    static class Person {
        public String name;

        @JsonUnwrapped
        public Address businessAddress;
    }

    public void testCaseInsensitiveUnwrap() throws Exception {
        ObjectMapper mapper = jsonMapperBuilder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .build();

        String json = aposToQuotes("{'name':'John','street':'Main St','city':'Boston'}");
        Person person = mapper.readValue(json, Person.class);
        assertNotNull(person);
        assertEquals("John", person.name);
        assertNotNull(person.businessAddress);
        assertEquals("Main St", person.businessAddress.street);
        assertEquals("Boston", person.businessAddress.city);
    }
}