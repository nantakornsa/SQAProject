package com.google.gson.functional;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Regression tests for deserializing concurrent map types (Gson issue 624).
 */
public class ConcurrentMapRegressionTest extends TestCase {
  private Gson gson;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    gson = new Gson();
  }

  public void testConcurrentMap() throws Exception {
    Type typeOfMap = new TypeToken<ConcurrentMap<Integer, String>>() {}.getType();
    ConcurrentMap<Integer, String> map = gson.fromJson("{\"123\":\"456\"}", typeOfMap);
    assertEquals(1, map.size());
    assertTrue(map.containsKey(123));
    assertEquals("456", map.get(123));
    String json = gson.toJson(map);
    assertEquals("{\"123\":\"456\"}", json);
  }

  public void testConcurrentNavigableMap() throws Exception {
    Type typeOfMap = new TypeToken<ConcurrentNavigableMap<Integer, String>>() {}.getType();
    ConcurrentNavigableMap<Integer, String> map = gson.fromJson("{\"123\":\"456\"}", typeOfMap);
    assertEquals(1, map.size());
    assertTrue(map.containsKey(123));
    assertEquals("456", map.get(123));
    assertTrue(map instanceof ConcurrentSkipListMap);
    String json = gson.toJson(map);
    assertEquals("{\"123\":\"456\"}", json);
  }
}