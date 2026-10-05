package com.fasterxml.jackson.databind.creators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InnerClassCreator72Test extends BaseMapTest
{
    static class Issue1501Bean {
        protected InnerClass inner;

        @JsonCreator
        public Issue1501Bean(@JsonProperty("a") InnerClass inner) {
            this.inner = inner;
        }

        public class InnerClass {
            public int f;

            public InnerClass(@JsonProperty("f") int f) {
                this.f = f;
            }
        }
    }

    public void testIssue1501() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Issue1501Bean bean = mapper.readValue("{\"a\":{\"f\":42}}", Issue1501Bean.class);
        assertNotNull(bean);
        assertNotNull(bean.inner);
        assertEquals(42, bean.inner.f);
    }
}