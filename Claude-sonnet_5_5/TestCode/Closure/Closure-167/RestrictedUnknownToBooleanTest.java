package com.google.javascript.rhino.jstype;

public class RestrictedUnknownToBooleanTest extends BaseJSTypeTestCase {

  public void testRestrictedUnknownTypeGivenToBooleanTrue() throws Exception {
    JSType restricted = UNKNOWN_TYPE.getRestrictedTypeGivenToBooleanOutcome(true);
    assertSame(CHECKED_UNKNOWN_TYPE, restricted);
  }

  public void testRestrictedUnknownTypeGivenToBooleanFalse() throws Exception {
    JSType restricted = UNKNOWN_TYPE.getRestrictedTypeGivenToBooleanOutcome(false);
    assertSame(UNKNOWN_TYPE, restricted);
  }
}