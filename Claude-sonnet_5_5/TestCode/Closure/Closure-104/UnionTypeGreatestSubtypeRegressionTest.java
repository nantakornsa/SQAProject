package com.google.javascript.rhino.jstype;

public class UnionTypeGreatestSubtypeRegressionTest extends BaseJSTypeTestCase {

  public void testGreatestSubtypeUnionTypes5() throws Exception {
    JSType errUnion = createUnionType(EVAL_ERROR_TYPE, URI_ERROR_TYPE);
    assertEquals(NO_OBJECT_TYPE,
        errUnion.getGreatestSubtype(STRING_OBJECT_TYPE));
  }
}