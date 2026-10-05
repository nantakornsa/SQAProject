package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for Closure-1 (issue 253): unused function parameters must
 * not be removed when global variable removal is disabled, as in
 * SIMPLE_OPTIMIZATIONS.
 */
public class RemoveUnusedVarsSimpleModeRegressionTest extends TestCase {

  public void testSimpleModeLeavesUnusedParams() {
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        SourceFile.fromCode("externs.js", ""),
        SourceFile.fromCode("input.js", "window.f = function(a) {};"),
        options);

    assertTrue(result.success);
    assertEquals("window.f=function(a){};", compiler.toSource());
  }
}