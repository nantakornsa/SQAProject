package com.google.javascript.jscomp;

/**
 * Regression test for Closure-114: NameAnalyzer must not associate the
 * references in a function that is assigned and immediately called
 * with the left-hand side of the assignment.
 */
public class NameAnalyzerAssignWithCallTest extends CompilerTestCase {

  private static final String EXTERNS =
      "var window, top;" +
      "var document;";

  public NameAnalyzerAssignWithCallTest() {
    super(EXTERNS);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    enableNormalize();
  }

  @Override
  protected int getNumRepetitions() {
    // Run the pass only once.
    return 1;
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new NameAnalyzer(compiler, true);
  }

  public void testAssignWithCall() {
    test("var fun, x; (fun = function(){ x; })();",
         "var x; (function(){ x; })();");
  }
}