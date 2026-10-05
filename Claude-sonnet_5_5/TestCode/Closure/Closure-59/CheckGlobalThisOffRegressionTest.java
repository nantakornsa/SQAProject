package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: an explicit request to turn off the globalThis
 * diagnostic group must be honored even when the warning level
 * (VERBOSE) would otherwise enable the global-this check.
 */
public class CheckGlobalThisOffRegressionTest extends TestCase {

  public void testCheckGlobalThisOff() {
    CompilerOptions options = new CompilerOptions();
    WarningLevel.VERBOSE.setOptionsForWarningLevel(options);
    options.setWarningLevel(DiagnosticGroups.GLOBAL_THIS, CheckLevel.OFF);

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs.js", "") },
        new JSSourceFile[] {
            JSSourceFile.fromCode("input.js", "function f() { this.a = 3; }")
        },
        options);

    assertTrue("Compilation should succeed", result.success);
    assertEquals("Expected no errors", 0, result.errors.length);
    assertEquals("Expected no warnings", 0, result.warnings.length);
  }
}