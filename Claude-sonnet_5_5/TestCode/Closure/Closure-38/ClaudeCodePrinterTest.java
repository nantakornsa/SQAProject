package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ClaudeCodePrinterTest extends CodePrinterTestBase {

  public void testMinusNegativeZero() {
    // Negative zero is weird, because we have to be able to distinguish
    // it from positive zero (there are some subtle differences in behavior).
    assertPrint("x- -0", "x- -0.0");
  }

  public void testMinusNegativeZeroSpacing() {
    // Ensure "x--0.0" (decrement followed by a number) is never produced.
    assertPrint("var a = x - -0", "var a=x- -0.0");
  }
}