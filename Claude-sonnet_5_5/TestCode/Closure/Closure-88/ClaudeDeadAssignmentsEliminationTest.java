package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ClaudeDeadAssignmentsEliminationTest extends CompilerTestCase {

  public ClaudeDeadAssignmentsEliminationTest() {
    super("", true);
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new DeadAssignmentsElimination(compiler);
  }

  public void testIssue297Regression() {
    // The assignment "x = parseInt(x.substr(1))" reads x in its RHS, so the
    // earlier assignment "x = p.id" is live and the second one must not be
    // removed or altered.
    testSame("function f(p) {" +
             " var x;" +
             " return ((x=p.id) && (x=parseInt(x.substr(1))) && x>0);" +
             "}");
  }

  public void testIssue297RegressionSimple() {
    testSame("function f(p) {" +
             " var x;" +
             " x = p.id;" +
             " x = x.substr(1);" +
             " return x;" +
             "}");
  }
}