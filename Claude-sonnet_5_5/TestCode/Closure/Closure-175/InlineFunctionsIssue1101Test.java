package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 1101: direct inlining must not be
 * performed when the argument may be affected by side effects of the
 * function body (e.g. a call that modifies a global argument).
 */
public class InlineFunctionsIssue1101Test extends CompilerTestCase {

  public InlineFunctionsIssue1101Test() {
    super("");
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    enableNormalize();
    enableMarkNoSideEffects();
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    compiler.resetUniqueNameId();
    return new InlineFunctions(
        compiler,
        compiler.getUniqueNameIdSupplier(),
        true,   // inline global functions
        true,   // inline local functions
        true,   // inline block functions
        true,   // assume strict this
        true);  // assume minimum capture
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testIssue1101() {
    // Inlining would yield "modifiyX() + x", which may change behavior
    // if modifiyX() modifies x. The call must not be inlined.
    testSame("function foo(a){return modifiyX() + a;} foo(x);");
  }
}