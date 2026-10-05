package com.google.javascript.rhino.jstype;

import com.google.javascript.rhino.testing.BaseJSTypeTestCase;

public class FunctionArityRegressionTest extends BaseJSTypeTestCase {

  public void testFunctionWithExtraRequiredParamsIsNotSubtype() {
    FunctionType noParams = registry.createFunctionType(UNDEFINED_TYPE);
    FunctionType twoParams =
        registry.createFunctionType(UNDEFINED_TYPE, NUMBER_TYPE, NUMBER_TYPE);

    // A function requiring two arguments cannot safely override/replace a
    // function that takes none.
    assertFalse(twoParams.isSubtype(noParams));
  }
}