package com.google.javascript.jscomp;

/**
 * Regression test: folding division or modulus by zero must not report a
 * JSC_DIVIDE_BY_0_ERROR. The expression should be left unchanged.
 */
public class PeepholeFoldConstantsTest extends CompilerTestCase {

  public PeepholeFoldConstantsTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new PeepholeOptimizationsPass(compiler, new PeepholeFoldConstants());
  }

  public void testDivideByZeroNoError() {
    fold("x = 1 / 0", "x = 1 / 0");
  }

  public void testModuloByZeroNoError() {
    fold("x = 1 % 0", "x = 1 % 0");
  }

  public void testFoldArithmeticWithZeroDivisors() {
    fold("x = 10 + 20", "x = 30");
    fold("x = 2 / 4", "x = 0.5");
    fold("x = 3 % 2", "x = 1");
    fold("x = 1 / 0", "x = 1 / 0");
    fold("x = 1 % 0", "x = 1 % 0");
  }
}