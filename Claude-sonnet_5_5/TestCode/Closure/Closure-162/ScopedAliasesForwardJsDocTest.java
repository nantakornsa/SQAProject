package com.google.javascript.jscomp;

import com.google.javascript.rhino.JSDocInfo;
import com.google.javascript.rhino.Node;

/**
 * Regression test for forward references to aliases in JsDoc inside
 * goog.scope blocks (Closure issue 548).
 */
public class ScopedAliasesForwardJsDocTest extends CompilerTestCase {

  private Node actualFn;
  private Node expectedFn;

  public ScopedAliasesForwardJsDocTest() {
    super("", false);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    final CompilerPass aliases = new ScopedAliases(
        compiler, null, CompilerOptions.NULL_ALIAS_TRANSFORMATION_HANDLER);
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node root) {
        actualFn = null;
        expectedFn = null;
        aliases.process(externs, root);
        find(root);
      }
    };
  }

  private void find(Node n) {
    if (n.getType() == com.google.javascript.rhino.Token.FUNCTION) {
      Node nameNode = n.getFirstChild();
      String name = nameNode.getString();
      if ("actual".equals(name)) {
        actualFn = n;
      } else if ("expected".equals(name)) {
        expectedFn = n;
      }
    }
    for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
      find(c);
    }
  }

  private void testScoped(String code, String expected) {
    test("goog.scope(function() {" + code + "});", expected);
  }

  private void verifyTypes() {
    assertNotNull(actualFn);
    assertNotNull(expectedFn);
    JSDocInfo actualInfo = actualFn.getJSDocInfo();
    JSDocInfo expectedInfo = expectedFn.getJSDocInfo();
    assertNotNull(actualInfo);
    assertNotNull(expectedInfo);
    Node actualType = actualInfo.getParameterType("x").getRoot();
    Node expectedType = expectedInfo.getParameterType("x").getRoot();
    assertEquals(expectedType.toStringTree(), actualType.toStringTree());
  }

  public void testForwardJsDoc() {
    testScoped(
        "/**\n" +
        " * @constructor\n" +
        " */\n" +
        "foo.Foo = function() {};" +
        "/** @param {Foo.Bar} x */ function actual(x) {3}" +
        "var Foo = foo.Foo;" +
        "/** @constructor */ Foo.Bar = function() {};" +
        "/** @param {foo.Foo.Bar} x */ function expected(x) {}",

        "/**\n" +
        " * @constructor\n" +
        " */\n" +
        "foo.Foo = function() {};" +
        "/** @param {foo.Foo.Bar} x */ function actual(x) {3}" +
        "/** @constructor */ foo.Foo.Bar = function() {};" +
        "/** @param {foo.Foo.Bar} x */ function expected(x) {}");
    verifyTypes();
  }
}