package com.google.javascript.jscomp;

/**
 * Regression test for issue 821: constant folding must not combine numeric
 * operands of an addition when the left operand may evaluate to a string
 * in only some branches.
 */
public class PeepholeFoldConstantsIssue821Test extends CompilerTestCase {

  public PeepholeFoldConstantsIssue821Test() {
    super("", false);
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    return new PeepholeOptimizationsPass(compiler, new PeepholeFoldConstants());
  }

  @Override
  protected int getNumRepetitions() {
    // Only one pass over the code is needed.
    return 1;
  }

  private void foldSame(String js) {
    testSame(js);
  }

  public void testIssue821() {
    foldSame("var a =(Math.random()>0.5? '1' : 2 ) + 3 + 4;");
    foldSame("var a = ((Math.random() ? 0 : 1) ||" +
             "(Math.random()>0.5? '1' : 2 )) + 3 + 4;");
  }
}