package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class PeepholeFoldConstantsTest extends CompilerTestCase {

  public PeepholeFoldConstantsTest() {
    super("");
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    enableLineNumberCheck(true);
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    CompilerPass peepholePass =
        new PeepholeOptimizationsPass(compiler, new PeepholeFoldConstants());
    return peepholePass;
  }

  public void testIssue522() {
    testSame("[][1] = 1;");
  }

  public void testIssue522AssignmentTargets() {
    testSame("[][1] = 1;");
    testSame("[][0] += 1;");
  }
}