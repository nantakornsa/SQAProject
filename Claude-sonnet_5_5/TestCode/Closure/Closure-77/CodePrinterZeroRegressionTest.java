package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test: a NUL character in a string literal must be printed
 * as "\0" rather than as "\u0000".
 */
public class CodePrinterZeroRegressionTest extends TestCase {

  private static String print(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    compiler.initOptions(options);

    Node n = compiler.parseTestCode(js);
    assertEquals(0, compiler.getErrorCount());

    String result = compiler.toSource(n).trim();
    if (result.endsWith(";")) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  private static void assertPrint(String js, String expected) {
    assertEquals(expected, print(js));
  }

  public void testZero() {
    assertPrint("var x ='\\0';", "var x=\"\\0\"");
    assertPrint("var x ='\\x00';", "var x=\"\\0\"");
    assertPrint("var x ='\\u0000';", "var x=\"\\0\"");
  }
}