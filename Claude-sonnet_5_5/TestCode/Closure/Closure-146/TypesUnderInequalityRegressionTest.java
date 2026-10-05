package com.google.javascript.jscomp;

import com.google.javascript.rhino.jstype.JSType;

public class TypesUnderInequalityRegressionTest extends CompilerTypeTestCase {

  public void testInequalityOfVoidWithVoidYieldsNoType() throws Exception {
    JSType.TypePair pair = VOID_TYPE.getTypesUnderInequality(VOID_TYPE);
    assertNotNull(pair);
    assertNotNull(pair.typeA);
    assertNotNull(pair.typeB);
    assertEquals(NO_TYPE, pair.typeA);
    assertEquals(NO_TYPE, pair.typeB);
  }
}