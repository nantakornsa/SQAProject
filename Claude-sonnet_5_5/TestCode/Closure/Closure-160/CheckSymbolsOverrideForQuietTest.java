package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: an explicit error level for the undefinedVars group must
 * be honored even when checkSymbols is off (e.g. warning_level=QUIET).
 */
public class CheckSymbolsOverrideForQuietTest extends TestCase {

  public void testCheckSymbolsOverrideForQuiet() {
    CompilerOptions options = new CompilerOptions();
    WarningLevel.QUIET.setOptionsForWarningLevel(options);
    options.checkSymbols = false;
    options.setWarningLevel(
        DiagnosticGroups.UNDEFINED_VARIABLES, CheckLevel.ERROR);

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "var extern;") },
        new JSSourceFile[] { JSSourceFile.fromCode("input", "x = 3;") },
        options);

    assertEquals("Expected exactly one error", 1, result.errors.length);
    assertEquals(VarCheck.UNDEFINED_VAR_ERROR, result.errors[0].getType());
  }
}