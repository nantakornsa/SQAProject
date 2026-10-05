package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CheckLevel;

public class CheckSideEffectsTest extends CompilerTestCase {

  public CheckSideEffectsTest() {
    this.parseTypeInfo = true;
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new CheckSideEffects(CheckLevel.WARNING, true);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  private final DiagnosticType e = CheckSideEffects.USELESS_CODE_ERROR;
  private final DiagnosticType ok = null;

  public void testUselessCodeInCommaExpression() {
    // A side-effect-free node in the middle of a nested comma expression
    // must be reported, even if it is the last child of an inner comma.
    test("var a, b; a = (bar(), 6, 7)",
         "var a, b; a = (bar(), JSCOMPILER_PRESERVE(6), 7)", e);
    test("var a, b; a = (bar(), bar(), 7, 8)",
         "var a, b; a = (bar(), bar(), JSCOMPILER_PRESERVE(7), 8)", e);
    test("var a, b; a = (b = 7, 6)", ok);
  }
}