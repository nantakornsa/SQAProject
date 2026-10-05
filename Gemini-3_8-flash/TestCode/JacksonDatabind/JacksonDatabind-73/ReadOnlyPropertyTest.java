package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test verifying that properties marked with {@link JsonProperty.Access#READ_ONLY}
 * are ignored during deserialization rather than failing with an UnrecognizedPropertyException.
 */
public class ReadOnlyPropertyTest extends BaseMapTest {

    static class Pojo935 {
        private String firstName;
        private String lastName;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        public String getFullName() {
            return firstName + " " + lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public void testReadOnlyIgnoredOnDeserialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Pojo935 result = mapper.readValue(
                "{\"firstName\":\"Bob\",\"lastName\":\"Burger\",\"fullName\":\"Bob Burger\"}",
                Pojo935.class
        );
        assertNotNull(result);
        assertEquals("Bob", result.getFirstName());
        assertEquals("Burger", result.getLastName());
    }
}