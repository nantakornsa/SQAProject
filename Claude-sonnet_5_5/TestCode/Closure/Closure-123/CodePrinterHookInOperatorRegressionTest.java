package com.google.javascript.jscomp;

public class CodePrinterHookInOperatorRegressionTest extends CodePrinterTestBase {

  public void testPrintInOperatorInHookInForLoopInit() {
    // The 'in' operator inside the "then" branch of a hook within a
    // for-loop initializer must stay parenthesized.
    assertPrintSame("for(a=c?0:(0 in d);;)foo()");
    assertPrintSame("for(a=c?(0 in d):0;;)foo()");
  }
}