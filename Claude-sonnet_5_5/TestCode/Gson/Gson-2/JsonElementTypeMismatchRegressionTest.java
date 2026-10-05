package com.google.gson.functional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import junit.framework.TestCase;

public class JsonElementTypeMismatchRegressionTest extends TestCase {
  private Gson gson;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    gson = new Gson();
  }

  public void testJsonElementTypeMismatch() {
    try {
      gson.fromJson("\"abc\"", JsonObject.class);
      fail();
    } catch (JsonSyntaxException expected) {
      assertEquals("Expected a com.google.gson.JsonObject but was com.google.gson.JsonPrimitive",
          expected.getMessage());
    }
  }

  public void testJsonElementMatchingTypeStillWorks() {
    JsonPrimitive primitive = gson.fromJson("\"abc\"", JsonPrimitive.class);
    assertEquals("abc", primitive.getAsString());

    JsonObject object = gson.fromJson("{\"a\":1}", JsonObject.class);
    assertEquals(1, object.get("a").getAsInt());
  }
}