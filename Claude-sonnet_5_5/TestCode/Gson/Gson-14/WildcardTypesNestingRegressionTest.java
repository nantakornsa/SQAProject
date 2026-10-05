package com.google.gson.internal.bind;

import com.google.gson.internal.$Gson$Types;
import junit.framework.TestCase;

/**
 * Regression test: nesting wildcard types via subtypeOf/supertypeOf must
 * flatten the wildcard instead of wrapping it in another wildcard.
 */
public class WildcardTypesNestingRegressionTest extends TestCase {

  public void testDoubleSupertype() {
    assertEquals($Gson$Types.supertypeOf(Number.class),
        $Gson$Types.supertypeOf($Gson$Types.supertypeOf(Number.class)));
  }

  public void testDoubleSubtype() {
    assertEquals($Gson$Types.subtypeOf(Number.class),
        $Gson$Types.subtypeOf($Gson$Types.subtypeOf(Number.class)));
  }

  public void testSuperSubtype() {
    assertEquals($Gson$Types.subtypeOf(Object.class),
        $Gson$Types.supertypeOf($Gson$Types.subtypeOf(Number.class)));
  }

  public void testSubSupertype() {
    assertEquals($Gson$Types.subtypeOf(Object.class),
        $Gson$Types.subtypeOf($Gson$Types.supertypeOf(Number.class)));
  }
}