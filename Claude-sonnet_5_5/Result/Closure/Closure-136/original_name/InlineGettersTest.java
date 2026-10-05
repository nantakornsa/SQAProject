package com.google.javascript.jscomp;

/**
 * Regression test for Closure-136 (issue 103): a method defined by an extern
 * must be left alone by the getter-inlining pass.
 */
public class InlineGettersTest extends CompilerTestCase {

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new InlineGetters(compiler);
  }

  public void testIssue2508576_1() {
    // Method defined by an extern should be left alone.
    String externs = "function alert(a) {}";
    testSame(externs, "({a:alert,b:alert}).a(\"a\")", null);
  }
}