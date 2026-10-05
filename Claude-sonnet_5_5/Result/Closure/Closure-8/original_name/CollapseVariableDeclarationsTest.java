package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 820: CollapseVariableDeclarations must not
 * redeclare function parameters, since that is incompatible with strict mode.
 */
public class CollapseVariableDeclarationsTest extends CompilerTestCase {

  @Override
  public CompilerPass getProcessor(Compiler compiler) {
    return new CollapseVariableDeclarations(compiler);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testIssue820() throws Exception {
    // Don't redeclare function parameters, this is incompatible with
    // strict mode.
    testSame("function f(a){ var b=1; a=2; var c; }");
  }
}