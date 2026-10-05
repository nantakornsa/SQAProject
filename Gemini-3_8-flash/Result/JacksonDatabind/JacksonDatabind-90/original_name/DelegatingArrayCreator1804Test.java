package com.fasterxml.jackson.databind.creators;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DelegatingArrayCreator1804Test extends BaseMapTest
{
    static abstract class MyType {
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public static MyType create(List<Object> list) {
            return new MyTypeImpl(list);
        }
    }

    static class MyTypeImpl extends MyType {
        protected List<Object> list;

        public MyTypeImpl(List<Object> list) {
            this.list = list;
        }
    }

    public void testDelegatingArray1804() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MyType thing = mapper.readValue("[]", MyType.class);
        assertNotNull(thing);
        assertTrue(thing instanceof MyTypeImpl);
    }
}