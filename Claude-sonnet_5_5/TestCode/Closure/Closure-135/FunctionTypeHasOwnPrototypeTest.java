package com.google.javascript.rhino.jstype;

/**
 * Regression test: a function type must report "prototype" as an own
 * property. Before the fix, {@code FunctionType} only overrode
 * {@code hasProperty}, so {@code hasOwnProperty("prototype")} returned false.
 */
public class FunctionTypeHasOwnPrototypeTest extends BaseJSTypeTestCase {

  public void testFunctionTypeHasOwnPrototypeProperty() {
    assertTrue(U2U_CONSTRUCTOR_TYPE.hasProperty("prototype"));
    assertTrue(U2U_CONSTRUCTOR_TYPE.hasOwnProperty("prototype"));
  }

  public void testFunctionTypeDoesNotHaveUnknownOwnProperty() {
    assertFalse(U2U_CONSTRUCTOR_TYPE.hasOwnProperty("noSuchProperty$$"));
  }
}