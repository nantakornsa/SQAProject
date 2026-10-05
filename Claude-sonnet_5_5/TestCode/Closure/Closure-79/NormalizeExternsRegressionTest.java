package com.google.javascript.jscomp;

/**
 * Regression test: Normalize must traverse the externs as well as the
 * main root. Without this, duplicate var declarations in externs are not
 * normalized and the compiler fails with an internal error.
 */
public class NormalizeExternsRegressionTest extends CompilerTestCase {

  public NormalizeExternsRegressionTest() {
    super("", false);
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new Normalize(compiler, false);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testIssue() {
    super.allowExternsChanges(true);
    test("var a,b,c; var a,b", "a(), b()", "a(), b()", null, null);
  }
}