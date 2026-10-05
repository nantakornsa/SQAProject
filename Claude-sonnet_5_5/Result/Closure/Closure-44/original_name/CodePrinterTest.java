package com.google.javascript.jscomp;

public class CodePrinterTest extends CodePrinterTestBase {

  public void testIssue620() {
    assertPrint("alert(/ / / / /);", "alert(/ // / /)");
    assertPrint("alert(/ // / /);", "alert(/ // / /)");
  }
}