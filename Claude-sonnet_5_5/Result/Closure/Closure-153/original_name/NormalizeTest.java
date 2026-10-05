package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Regression test for duplicate var declarations where one declaration is in
 * externs and the other is in source (Closure-153). The normalizer must allow
 * the duplicate instead of removing or rewriting it incorrectly.
 */
public class NormalizeTest extends CompilerTestCase {

  public NormalizeTest() {
    super("function alert() {}");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node root) {
        Normalize normalize = new Normalize(compiler, false);
        normalize.process(externs, root);
      }
    };
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testDuplicateVarInExterns() {
    test("var extern;",
         "/** @suppress {duplicate} */ var extern = 3;", "var extern = 3;",
         null, null);
  }
}