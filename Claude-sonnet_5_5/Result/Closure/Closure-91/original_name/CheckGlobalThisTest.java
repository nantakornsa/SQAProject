package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class CheckGlobalThisTest extends CompilerTestCase {

  public CheckGlobalThisTest() {
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
  public int getNumRepetitions() {
    return 1;
  }

  public void testLendsAnnotation3() {
    testSame("/** @constructor */ function F() {}" +
        "dojo.declare(F, /** @lends {F.prototype} */ (" +
        "    {foo: function() { return this.foo; }}));");
  }

  public void testLendsAnnotationWithPrototypeInObjectLiteralKey() {
    testSame("/** @constructor */ function G() {}" +
        "dojo.declare(G, /** @lends {G.prototype} */ (" +
        "    {bar: function() { return this.bar; }}));");
  }
}