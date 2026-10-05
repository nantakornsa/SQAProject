package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for issue 725: accessing a property that exists only on
 * another record type (not on the declared one) must produce a
 * "Property ... never defined" warning.
 */
public class TypeCheckRecordIssue725Test extends TestCase {

  public void testIssue725() throws Exception {
    String js =
        "/** @typedef {{name: string}} */ var RecordType1;" +
        "/** @typedef {{name2: string}} */ var RecordType2;" +
        "/** @param {RecordType1} rec */ function f(rec) {" +
        "  alert(rec.name2);" +
        "}";

    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    JSSourceFile extern =
        JSSourceFile.fromCode("externs", "function alert(x) {}");
    JSSourceFile input = JSSourceFile.fromCode("input", js);

    compiler.compile(
        new JSSourceFile[] {extern},
        new JSSourceFile[] {input},
        options);

    boolean found = false;
    StringBuilder seen = new StringBuilder();
    for (JSError warning : compiler.getWarnings()) {
      seen.append(warning.description).append("\n");
      if (warning.description.contains("Property name2 never defined on rec")) {
        found = true;
      }
    }

    assertTrue("expected a warning 'Property name2 never defined on rec', "
        + "but got: " + seen, found);
  }
}