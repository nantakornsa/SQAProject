package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CheckLevel;

public class CheckSideEffectsTest extends CompilerTestCase {

  public CheckSideEffectsTest() {
    this.parseTypeInfo = false;
  }

  private final DiagnosticType e = CheckSideEffects.USELESS_CODE_ERROR;
  private final DiagnosticType ok = null; // no warning

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new CheckSideEffects(CheckLevel.WARNING, true);
  }

  @Override
  public int getNumRepetitions() {
    return 1;
  }

  public void testUselessCodeInForLoopComma() {
    // Useless expression as the last element of a comma in a FOR increment.
    test("var i; for (i = 0; i < 3; i++, i == 5) {}",
         "var i; for (i = 0; i < 3; i++, JSCOMPILER_PRESERVE(i == 5)) {}", e);
  }

  public void testUselessCodeInForLoopInitComma() {
    // Useless expression as the last element of a comma in a FOR initializer.
    test("var i; for (i = 0, i == 5; i < 3; i++) {}",
         "var i; for (i = 0, JSCOMPILER_PRESERVE(i == 5); i < 3; i++) {}", e);
  }

  public void testNoWarningForLoopWithSideEffects() {
    test("var i; for (i = 0, i = 1; i < 3; i++, i = 2) {}", ok);
  }
}