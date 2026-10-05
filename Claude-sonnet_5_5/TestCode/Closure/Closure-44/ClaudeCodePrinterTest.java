package com.google.javascript.jscomp;

public class ClaudeCodePrinterTest extends CodePrinterTestBase {

  public void testIssue620() {
    assertPrint("alert(/ / / / /);", "alert(/ // / /)");
    assertPrint("alert(/ // / /);", "alert(/ // / /)");
  }
}