package com.google.javascript.rhino.jstype;

public class LeastFunctionTypeEmptyTest extends BaseJSTypeTestCase {

  public void testLeastFunctionTypeIsEmpty() throws Exception {
    JSType leastFunction = registry.getNativeType(JSTypeNative.LEAST_FUNCTION_TYPE);
    assertTrue(leastFunction.isEmptyType());
  }

  public void testGreatestFunctionTypeIsNotEmpty() throws Exception {
    JSType greatestFunction =
        registry.getNativeType(JSTypeNative.GREATEST_FUNCTION_TYPE);
    assertFalse(greatestFunction.isEmptyType());
  }
}