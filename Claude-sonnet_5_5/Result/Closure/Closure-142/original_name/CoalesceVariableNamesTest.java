package com.google.javascript.jscomp;

/**
 * Regression test for the IE sort() work-around in CoalesceVariableNames:
 * parameters of two-argument functions must be treated as escaped.
 */
public class CoalesceVariableNamesTest extends CompilerTestCase {

  private boolean usePseudoName = false;

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CoalesceVariableNames(compiler, usePseudoName);
  }

  @Override
  public int getNumRepetitions() {
    return 1;
  }

  public void testParameter4() {
    // Make sure that we do not merge two-arg functions because of the
    // IE sort bug (see comments in computeEscaped)
    test("function FUNC(x, y) {var a,b; y; a=0; a; x; b=0; b}",
         "function FUNC(x, y) {var a; y; a=0; a; x; a=0; a}");
  }
}