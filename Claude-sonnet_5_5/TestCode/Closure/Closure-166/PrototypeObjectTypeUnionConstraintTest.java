package com.google.javascript.rhino.jstype;

import com.google.javascript.rhino.testing.BaseJSTypeTestCase;

public class PrototypeObjectTypeUnionConstraintTest extends BaseJSTypeTestCase {

  private JSType optional(JSType type) {
    return registry.createUnionType(type, VOID_TYPE);
  }

  private JSType record(String name, JSType type) {
    RecordTypeBuilder builder = new RecordTypeBuilder(registry);
    builder.addProperty(name, type, null);
    return builder.build();
  }

  public void testMatchConstraintWithUnionOfRecords() {
    JSType recordA = record("a", optional(BOOLEAN_TYPE));
    JSType recordB = record("b", optional(STRING_TYPE));
    JSType union = registry.createUnionType(recordA, recordB);

    ObjectType target = registry.createAnonymousObjectType();
    target.matchConstraint(union);

    assertEquals("{a: (boolean|undefined), b: (string|undefined)}",
        target.toString());
  }

  public void testMatchConstraintWithUnionOfRecordAndNull() {
    JSType recordA = record("prop", optional(STRING_TYPE));
    JSType union = registry.createUnionType(recordA, NULL_TYPE);

    ObjectType target = registry.createAnonymousObjectType();
    target.matchConstraint(union);

    assertEquals("{prop: (string|undefined)}", target.toString());
  }
}