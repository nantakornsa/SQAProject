package com.google.javascript.jscomp;

public class ClaudeUnreachableCodeEliminationTest extends CompilerTestCase {

  public ClaudeUnreachableCodeEliminationTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new UnreachableCodeElimination(compiler, true);
  }

  @Override
  protected int getNumRepetitions() {
    // Only run the pass once so that each test shows what a single pass removes.
    return 1;
  }

  public void testCascadedRemovalOfUnlessUnconditonalJumps() {
    test("switch (a) { case 'a': break; case 'b': break; case 'c': break }",
         "switch (a) { case 'a': break; case 'b': case 'c': }");
    // Only one break removed per pass.
    test("switch (a) { case 'a': break; case 'b': case 'c': }",
         "switch (a) { case 'a': case 'b': case 'c': }");

    test("function foo() {" +
         "  switch (a) { case 'a':return; case 'b':return; case 'c':return }}",
         "function foo() { switch (a) { case 'a':return; case 'b': case 'c': }}");
    test("function foo() {" +
         "  switch (a) { case 'a':return; case 'b': case 'c': }}",
         "function foo() { switch (a) { case 'a': case 'b': case 'c': }}");

    testSame("function foo() {" +
             "switch (a) { case 'a':return 2; case 'b':return 1}}");
  }
}