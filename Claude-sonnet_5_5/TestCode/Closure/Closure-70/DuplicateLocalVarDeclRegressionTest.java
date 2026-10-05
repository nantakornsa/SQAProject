package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: a local var that redeclares a function parameter with a
 * different type must produce both a duplicate declaration warning and a
 * type mismatch warning.
 */
public class DuplicateLocalVarDeclRegressionTest extends TestCase {

  public void testDuplicateLocalVarDecl() throws Exception {
    String js =
        "/** @param {number} x */\n" +
        "function f(x) { /** @type {string} */ var x = ''; }";

    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    Compiler compiler = new Compiler();
    compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("testcode", js) },
        options);

    JSError[] warnings = compiler.getWarnings();
    StringBuilder sb = new StringBuilder();
    for (JSError w : warnings) {
      sb.append(w.toString()).append("\n");
    }

    assertEquals("Unexpected warnings: " + sb, 2, warnings.length);

    boolean sawDup = false;
    boolean sawMismatch = false;
    for (JSError w : warnings) {
      String msg = w.description;
      if (msg.contains("variable x redefined with type string")
          && msg.contains("with type number")) {
        sawDup = true;
      }
      if (msg.contains("initializing variable")
          && msg.contains("found   : string")
          && msg.contains("required: number")) {
        sawMismatch = true;
      }
    }
    assertTrue("Expected duplicate declaration warning: " + sb, sawDup);
    assertTrue("Expected type mismatch warning: " + sb, sawMismatch);
  }
}