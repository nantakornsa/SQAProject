package com.fasterxml.jackson.databind.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MixinOverride771Test extends BaseMapTest {

    interface Mixin1 {
        @JsonProperty("stuff")
        String getFoo();
    }

    interface Mixin2 {
        @JsonProperty("bar")
        String getFoo();
    }

    static class Target {
        public String getFoo() {
            return "result";
        }
    }

    public void testMixinOverride() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Target.class, Mixin1.class);
        mapper.addMixIn(Target.class, Mixin2.class);

        String json = mapper.writeValueAsString(new Target());
        assertEquals("{\"bar\":\"result\"}", json);
    }
}