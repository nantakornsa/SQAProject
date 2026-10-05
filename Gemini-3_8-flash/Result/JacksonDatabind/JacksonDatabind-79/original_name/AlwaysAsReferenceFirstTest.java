package com.fasterxml.jackson.databind.objectid;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlwaysAsReferenceFirstTest extends BaseMapTest {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    static class AlwaysClass {
        public int id;
        public int value;

        public AlwaysClass(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    static class OtherClass {
        public int id;
        public int value;

        public OtherClass(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    static class Issue1607Wrapper {
        public AlwaysClass alwaysClass;

        @JsonIdentityReference(alwaysAsId = true)
        public OtherClass alwaysProp;

        public Issue1607Wrapper(AlwaysClass alwaysClass, OtherClass alwaysProp) {
            this.alwaysClass = alwaysClass;
            this.alwaysProp = alwaysProp;
        }
    }

    public void testIssue1607() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Issue1607Wrapper wrapper = new Issue1607Wrapper(new AlwaysClass(1, 13), new OtherClass(2, 23));
        String json = mapper.writeValueAsString(wrapper);
        assertEquals("{\"alwaysClass\":1,\"alwaysProp\":2}", json);
    }
}