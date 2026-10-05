package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CodePrinter;
import com.google.javascript.rhino.Node;

public class CodePrinterIssue1062Test extends CodePrinterTestBase {

  public void testIssue1062Regression() {
    assertPrintSame("3*(4%3*5)");
  }

  public void testAssociativeParensPreserved() {
    assertPrint("var a,b,c; a || (b || c); a * (b * c); a | (b | c)",
        "var a,b,c;a||(b||c);a*(b*c);a|(b|c)");
  }

  private void assertPrint(String js, String expected) {
    parse(expected); // validate the expected string is valid JS
    assertEquals(expected,
        parsePrint(js, false, CodePrinter.DEFAULT_LINE_LENGTH_THRESHOLD));
  }

  private void assertPrintSame(String js) {
    assertPrint(js, js);
  }
}