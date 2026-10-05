package com.google.javascript.jscomp;

/**
 * Regression test for aliases of loop-scoped variables being inlined
 * incorrectly (Closure issue 174).
 */
public class ClaudeInlineVariablesTest extends CompilerTestCase {

  public ClaudeInlineVariablesTest() {
    super("function externs() {}");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new InlineVariables(
        compiler, InlineVariables.Mode.ALL, true);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testNoInlineAliasesInLoop() {
    testSame(
        "function f() { " +
        "  for (var i = 0; i < 5; i++) {" +
        "    var x = extern();" +
        "    (function() {" +
        "       var y = x; window.setTimeout(function() { extern(y); }, 0);" +
        "     })();" +
        "  }" +
        "}");
  }
}