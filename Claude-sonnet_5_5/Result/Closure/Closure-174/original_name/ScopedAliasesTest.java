package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CompilerOptions.AliasTransformationHandler;

public class ScopedAliasesTest extends CompilerTestCase {

  private static String SCOPE_NAMESPACE = "var $jscomp = $jscomp || {}; $jscomp.scope = {};";

  private AliasTransformationHandler transformationHandler =
      CompilerOptions.NULL_ALIAS_TRANSFORMER_HANDLER;

  public ScopedAliasesTest() {
    parseTypeInfo = true;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    compareJsDoc = true;
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new ScopedAliases(compiler, transformationHandler);
  }

  public void testIssue1103a() {
    test("goog.scope(function () {" +
         "  var a;" +
         "  foo.bar = function () { a = 1; };" +
         "});",
         SCOPE_NAMESPACE + "foo.bar = function () { $jscomp.scope.a = 1; }");
  }

  public void testIssue1103b() {
    test("goog.scope(function () {" +
         "  var a = foo, b, c = 1, d = bar;" +
         "});",
         SCOPE_NAMESPACE +
         "$jscomp.scope.c = 1; ");
  }
}