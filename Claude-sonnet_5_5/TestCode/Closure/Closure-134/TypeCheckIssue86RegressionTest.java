package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for Closure issue 86: a function defined on a prototype
 * that overrides a method of an implemented interface must be type checked
 * against the interface's declaration.
 */
public class TypeCheckIssue86RegressionTest extends TestCase {

  public void testIssue86() throws Exception {
    String js =
        "/** @interface */ function I() {}\n" +
        "/** @return {number} */ I.prototype.get = function(){};\n" +
        "/** @constructor \n * @implements {I} */ function F() {}\n" +
        "/** @override */ F.prototype.get = function() { return true; };";

    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    Compiler compiler = new Compiler();
    compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("input", js) },
        options);

    JSError[] warnings = compiler.getWarnings();
    boolean found = false;
    for (JSError warning : warnings) {
      if (warning.description.contains("inconsistent return type")) {
        found = true;
      }
    }
    assertTrue("expected an 'inconsistent return type' warning", found);
  }
}