package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for Closure issue 810: property assignment on a
 * null value must produce a "No properties on this expression" warning.
 */
public class TypeCheckIssue810Test extends TestCase {

  public void testGetpropAssignOnNullWarns() throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    JSSourceFile extern = JSSourceFile.fromCode("externs", "");
    JSSourceFile input = JSSourceFile.fromCode("input",
        "var x = null; x.prop = 3;");

    compiler.compile(extern, input, options);

    JSError[] warnings = compiler.getWarnings();
    assertTrue("expected a warning", warnings.length >= 1);

    boolean found = false;
    for (JSError warning : warnings) {
      if (warning.description.contains("No properties on this expression")) {
        found = true;
      }
    }
    assertTrue("expected 'No properties on this expression' warning", found);
  }
}