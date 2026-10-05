package com.google.javascript.rhino;

import junit.framework.TestCase;

public class JSDocInfoBuilderBlockDescriptionTest extends TestCase {

  public void testRecordBlockDescriptionMarksBuilderPopulatedWithoutDocParsing() {
    // Documentation parsing is disabled, as for a plain compile.
    JSDocInfoBuilder builder = new JSDocInfoBuilder(false);
    assertFalse(builder.isPopulated());

    builder.recordBlockDescription("This is a jsdoc comment");

    // A JSDoc block comment must be treated as populated even when
    // documentation is not retained. The buggy version leaves the builder
    // unpopulated.
    assertTrue(builder.isPopulated());
  }
}