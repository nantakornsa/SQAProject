package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestNullObjectId742 extends BaseMapTest {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    static class NullIdBean {
        public String id;
        public String name;
    }

    public void testNullObjectId() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NullIdBean bean = mapper.readValue("{\"id\":null,\"name\":\"test\"}", NullIdBean.class);
        assertNotNull(bean);
        assertNull(bean.id);
        assertEquals("test", bean.name);
    }
}