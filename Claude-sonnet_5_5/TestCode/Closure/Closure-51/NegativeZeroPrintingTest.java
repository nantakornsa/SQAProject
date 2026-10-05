package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test for Issue 582: negative zero must be printed as "-0.0"
 * and not as "0" (or "-0" without a fraction) by the code printer.
 */
public class NegativeZeroPrintingTest extends TestCase {

  private static String print(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    compiler.initOptions(options);
    Node root = compiler.parseTestCode(js);
    assertEquals(0, compiler.getErrorCount());
    return compiler.toSource(root);
  }

  public void testIssue582() {
    String result = print("var x = -0.0;");
    assertTrue("Negative zero was lost, got: " + result,
        result.contains("var x=-0.0"));
  }

  public void testPositiveZeroUnchanged() {
    String result = print("var x = 0;");
    assertTrue("Unexpected output: " + result, result.contains("var x=0"));
    assertFalse("Unexpected output: " + result, result.contains("-0"));
  }
}