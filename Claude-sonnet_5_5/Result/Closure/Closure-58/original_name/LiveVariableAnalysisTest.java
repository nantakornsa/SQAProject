package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for an expression (non-name) on the left-hand side of a
 * for-in loop in {@link LiveVariablesAnalysis}.
 */
public class LiveVariableAnalysisTest extends TestCase {

  public void testExpressionInForIn() {
    CompilerOptions options = new CompilerOptions();
    // Dead assignment elimination runs LiveVariablesAnalysis.
    options.deadAssignmentElimination = true;

    Compiler compiler = new Compiler();
    String code =
        "function f(foo) { var a = [0]; X: for (a[1] in foo) { } return a; }";

    Result result = compiler.compile(
        JSSourceFile.fromCode("externs", ""),
        JSSourceFile.fromCode("input", code),
        options);

    assertTrue(result.success);
    assertEquals(0, compiler.getErrorCount());
  }
}