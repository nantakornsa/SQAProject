package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Regression test for CheckGlobalThis: assignments to a property of a
 * prototype property (e.g. a.prototype.b.c) must still be reported when
 * the function uses the global "this".
 */
public class ClaudeCheckGlobalThisTest extends CompilerTestCase {

  public ClaudeCheckGlobalThisTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node root) {
        NodeTraversal.traverse(compiler, root, new CheckGlobalThis(compiler));
      }
    };
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  private void testFailure(String js) {
    test(js, null, CheckGlobalThis.GLOBAL_THIS);
  }

  public void testPropertyOfMethod() {
    testFailure("a.protoype.b = {}; " +
        "a.prototype.b.c = function() { this.foo = 3; };");
  }
}