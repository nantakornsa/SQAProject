package com.google.javascript.jscomp;

/**
 * Regression test for unsigned right shift folding (Closure-97).
 * Tests that {@code >>>} with a zero shift amount on negative numbers
 * yields the correct unsigned 32-bit value instead of a signed int.
 */
public class ClaudePeepholeFoldConstantsTest extends CompilerTestCase {

  public ClaudePeepholeFoldConstantsTest() {
    super("");
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new PeepholeOptimizationsPass(compiler, new PeepholeFoldConstants());
  }

  @Override
  protected int getNumRepetitions() {
    // Reduce this to 1 if we get better expression evaluators.
    return 1;
  }

  private void fold(String js, String expected) {
    test(js, expected);
  }

  public void testFoldUnsignedRightShift() {
    fold("x = 10 >>> 1", "x = 5");
    fold("x = -1 >>> 1", "x = 2147483647"); // 0x7fffffff
    fold("x = -1 >>> 0", "x = 4294967295"); // 0xffffffff
    fold("x = -2 >>> 0", "x = 4294967294"); // 0xfffffffe
  }
}