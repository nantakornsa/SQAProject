package com.fasterxml.jackson.databind.jsontype;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiTypeRefinementForMap1215Test extends BaseMapTest
{
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
    public interface HasUniqueId {
        String getId();
    }

    public static class UniqueIdBean implements HasUniqueId {
        protected String id;

        public UniqueIdBean() { }
        public UniqueIdBean(String id) { this.id = id; }

        @Override
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
    }

    public static class UniqueIdMap<V> extends HashMap<String, V> {
        private static final long serialVersionUID = 1L;
    }

    public static class UniqueIdMapBean {
        public UniqueIdMap<HasUniqueId> map;
    }

    public void testMapRefinement() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        UniqueIdMapBean bean = new UniqueIdMapBean();
        bean.map = new UniqueIdMap<HasUniqueId>();
        bean.map.put("key", new UniqueIdBean("abc"));
        String json = mapper.writeValueAsString(bean);

        UniqueIdMapBean result = mapper.readValue(json, UniqueIdMapBean.class);
        assertNotNull(result);
        assertNotNull(result.map);
        HasUniqueId value = result.map.get("key");
        assertNotNull(value);
        assertEquals("abc", value.getId());
    }
}