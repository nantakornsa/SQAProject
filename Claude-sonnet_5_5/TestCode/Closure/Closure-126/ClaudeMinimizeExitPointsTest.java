package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ClaudeMinimizeExitPointsTest extends CompilerTestCase {

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node js) {
        NodeTraversal.traverse(compiler, js, new MinimizeExitPoints(compiler));
      }
    };
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testDontRemoveBreakInTryFinally() throws Exception {
    foldSame("function f() {b:try{throw 9} finally {break b} return 1;}");
  }

  public void testDontRemoveBreakInTryFinallyWithLabelAndCode() throws Exception {
    foldSame("function f() {b:try{throw 9} finally {break b} return 1;}");
  }
}