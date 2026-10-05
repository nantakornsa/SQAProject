package com.google.javascript.jscomp;

public class CodePrinterTest extends CodePrinterTestBase {

  public void testZero() {
    assertPrint("var x ='\\0';", "var x=\"\\000\"");
    assertPrint("var x ='\\x00';", "var x=\"\\000\"");
    assertPrint("var x ='\\u0000';", "var x=\"\\000\"");
    assertPrint("var x ='\\u00003';", "var x=\"\\0003\"");
  }
}