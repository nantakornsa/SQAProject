package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for Issue 124: type inference of an inferred global
 * variable read from an inner scope must not use the (stale) type of the
 * outer scope's slot.
 */
public class Issue124RegressionTest extends TestCase {

  private JSError[] compileWithTypeChecking(String js) {
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    WarningLevel.VERBOSE.setOptionsForWarningLevel(options);

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("input", js) },
        options);
    return result.warnings;
  }

  public void testIssue124() throws Exception {
    JSError[] warnings = compileWithTypeChecking(
        "var t = null;" +
        "function test() {" +
        "  if (t != null) { t = null; }" +
        "  t = 1;" +
        "}");
    StringBuilder sb = new StringBuilder();
    for (JSError w : warnings) {
      sb.append(w.toString()).append('\n');
    }
    assertEquals("unexpected warning(s): " + sb, 0, warnings.length);
  }
}