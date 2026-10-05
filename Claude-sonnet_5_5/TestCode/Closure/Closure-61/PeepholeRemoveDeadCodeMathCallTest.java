package com.google.javascript.jscomp;

/**
 * Regression test: calls to functions in the "Math" namespace have no side
 * effects and must be removed by the peephole dead code remover.
 */
public class PeepholeRemoveDeadCodeMathCallTest extends CompilerTestCase {

  public PeepholeRemoveDeadCodeMathCallTest() {
    super("function alert() {}");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new PeepholeOptimizationsPass(compiler, new PeepholeRemoveDeadCode());
  }

  @Override
  protected int getNumRepetitions() {
    // Run the pass only once.
    return 1;
  }

  public void testMathCallRemoved() {
    test("Math.sin(0);", "");
  }

  public void testMathCallWithMultipleArgsRemoved() {
    test("Math.max(1, 2);", "");
  }

  public void testNonMathCallNotRemoved() {
    testSame("foo.sin(0);");
  }
}