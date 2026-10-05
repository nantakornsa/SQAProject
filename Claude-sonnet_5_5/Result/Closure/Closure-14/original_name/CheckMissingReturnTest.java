package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for issue 779: a "return" inside a "try" block with a
 * "finally" block was wrongly reported as a missing return statement.
 */
public class CheckMissingReturnTest extends TestCase {

  private static int countMissingReturnWarnings(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    JSSourceFile extern = JSSourceFile.fromCode(
        "externs", "function alert(x) {} function f() {}");
    JSSourceFile input = JSSourceFile.fromCode("testcode", js);

    compiler.compile(extern, input, options);

    int count = 0;
    for (JSError error : compiler.getWarnings()) {
      if (error.description.contains("Missing return statement")) {
        count++;
      }
    }
    for (JSError error : compiler.getErrors()) {
      if (error.description.contains("Missing return statement")) {
        count++;
      }
    }
    return count;
  }

  public void testIssue779() {
    String js =
        "/** @return {number} */ function g() {" +
        "var a = f(); try { alert(); if (a > 0) return 1; }" +
        "finally { a = 5; } return 2; }";
    assertEquals(0, countMissingReturnWarnings(js));
  }
}