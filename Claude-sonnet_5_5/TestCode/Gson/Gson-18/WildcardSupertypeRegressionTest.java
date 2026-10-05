package com.google.gson.functional;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 * Regression test for issue 1107: wildcard types must be resolved to their
 * upper bound when looking up supertypes.
 */
public class WildcardSupertypeRegressionTest extends TestCase {

  private static class SmallClass {
    String inSmall;
  }

  private static class BigClass {
    Map<String, ? extends List<SmallClass>> inBig;
  }

  public void testIssue1107() {
    String json = "{\n" +
            "  \"inBig\": {\n" +
            "    \"key\": [\n" +
            "      { \"inSmall\": \"hello\" }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
    BigClass bigClass = new Gson().fromJson(json, BigClass.class);
    SmallClass small = bigClass.inBig.get("key").get(0);
    assertNotNull(small);
    assertEquals("hello", small.inSmall);
  }
}