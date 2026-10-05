package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ScopedAliasesTest extends CompilerTestCase {

  private static String EXTERNS = "var window;";

  private static final String SCOPE_NAMESPACE =
      "/** @const */ var $jscomp = $jscomp || {}; /** @const */ $jscomp.scope = {};";

  public ScopedAliasesTest() {
    super(EXTERNS);
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new ScopedAliases(
        compiler, null, CompilerOptions.NULL_ALIAS_TRANSFORMATION_HANDLER);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testIssue1144() {
    test("var ns = {};" +
         "ns.sub = {};" +
         "/** @constructor */ ns.sub.C = function () {};" +
         "goog.scope(function () {" +
         "  var sub = ns.sub;" +
         "  /** @type {sub.C} */" +
         "  var x = null;" +
         "});",
         SCOPE_NAMESPACE +
         "var ns = {};" +
         "ns.sub = {};" +
         "/** @constructor */ ns.sub.C = function () {};" +
         "$jscomp.scope.x = null;");
  }
}