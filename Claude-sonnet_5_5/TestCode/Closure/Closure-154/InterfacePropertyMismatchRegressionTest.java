package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: a constructor that implements an interface but declares a
 * property of an incompatible type on the instance (via {@code this.prop = ...})
 * must produce a HIDDEN_INTERFACE_PROPERTY_MISMATCH warning.
 */
public class InterfacePropertyMismatchRegressionTest extends TestCase {

  public void testInterfaceInheritanceCheck12() throws Exception {
    String code =
        "/** @interface */ function I() {};\n" +
        "/** @type {string} */ I.prototype.foobar;\n" +
        "/** \n * @constructor \n * @implements {I} */\n" +
        "function C() {\n" +
        "/** \n * @type {number} */ this.foobar = 2;};\n" +
        "/** @type {I} */ \n var test = new C();";

    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    compiler.compile(
        JSSourceFile.fromCode("externs", ""),
        JSSourceFile.fromCode("input", code),
        options);

    String expected =
        "mismatch of the foobar property type and the type of the property" +
        " it overrides from interface I\n" +
        "original: string\n" +
        "override: number";

    boolean found = false;
    StringBuilder seen = new StringBuilder();
    for (JSError warning : compiler.getWarnings()) {
      seen.append(warning.description).append("\n---\n");
      if (expected.equals(warning.description)) {
        found = true;
      }
    }
    assertTrue("expected a warning: " + expected + "\nbut got:\n" + seen,
        found);
  }
}