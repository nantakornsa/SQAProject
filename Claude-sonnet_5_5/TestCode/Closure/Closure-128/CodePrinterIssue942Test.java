package com.google.javascript.jscomp;

public class CodePrinterIssue942Test extends CodePrinterTestBase {

  public void testIssue942() {
    assertPrint("var x = {0: 1};", "var x={0:1}");
  }

  public void testIssue942ZeroKeyAmongOthers() {
    assertPrint("var x = {0: 1, 1: 2};", "var x={0:1,1:2}");
  }

  public void testIsSimpleNumber() {
    assertTrue(CodeGenerator.isSimpleNumber("0"));
    assertTrue(CodeGenerator.isSimpleNumber("10"));
    assertTrue(CodeGenerator.isSimpleNumber("7"));
    assertFalse(CodeGenerator.isSimpleNumber(""));
    assertFalse(CodeGenerator.isSimpleNumber("01"));
    assertFalse(CodeGenerator.isSimpleNumber("1a"));
  }
}