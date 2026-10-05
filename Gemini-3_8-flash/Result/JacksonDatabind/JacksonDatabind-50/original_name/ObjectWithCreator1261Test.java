package com.fasterxml.jackson.databind.objectid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectWithCreator1261Test extends BaseMapTest {

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
    static class Parent {
        public int a;
        public Child child;

        @JsonCreator
        public Parent(@JsonProperty("a") int a) {
            this.a = a;
        }
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
    static class Child {
        public int b;
        public Parent parent;

        @JsonCreator
        public Child(@JsonProperty("b") int b) {
            this.b = b;
        }
    }

    public void testObjectIds1261() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        Parent p = new Parent(1);
        Child c = new Child(2);
        p.child = c;
        c.parent = p;

        String json = mapper.writeValueAsString(p);
        Parent deserializedParent = mapper.readValue(json, Parent.class);

        assertNotNull(deserializedParent);
        assertEquals(1, deserializedParent.a);
        assertNotNull(deserializedParent.child);
        assertEquals(2, deserializedParent.child.b);
        assertSame(deserializedParent, deserializedParent.child.parent);
    }
}