package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for Closure issue 791: a record type containing a
 * function-typed property was incorrectly reported as a mismatch against an
 * object whose function property was assigned later.
 */
public class Issue791RegressionTest extends TestCase {

  public void testIssue791() throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    JSSourceFile externs = JSSourceFile.fromCode("externs", "var externVar;");
    JSSourceFile input = JSSourceFile.fromCode(
        "input",
        "/** @param {{func: function()}} obj */" +
        "function test1(obj) {}" +
        "var fnStruc1 = {};" +
        "fnStruc1.func = function() {};" +
        "test1(fnStruc1);");

    Result result = compiler.compile(externs, input, options);

    StringBuilder sb = new StringBuilder();
    for (JSError warning : result.warnings) {
      sb.append(warning.toString()).append("\n");
    }
    assertEquals("unexpected warning(s): " + sb, 0, result.warnings.length);
    assertEquals(0, result.errors.length);
  }
}