package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CheckLevel;

/**
 * Regression test: reverse abstract interpretation must not throw when
 * trying to refine a "this" reference.
 */
public class ChainableReverseAbstractInterpreterThisTest
    extends CompilerTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    enableTypeCheck(CheckLevel.WARNING);
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(com.google.javascript.rhino.Node externs,
                          com.google.javascript.rhino.Node root) {
        // No-op; type checking is run by the test harness.
      }
    };
  }

  public void testNoThisInference() {
    testSame(
        "/** @this {Object} */\n" +
        "function f() {\n" +
        "  var out = 3;\n" +
        "  if (this == null) {\n" +
        "    out = this;\n" +
        "  }\n" +
        "  if (this !== null) {\n" +
        "    out = 4;\n" +
        "  }\n" +
        "  return out;\n" +
        "}");
  }
}