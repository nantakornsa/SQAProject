package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CompilerOptions.AliasTransformationHandler;

public class ScopedAliasesTest extends CompilerTestCase {

  private AliasTransformationHandler transformationHandler =
      CompilerOptions.NULL_ALIAS_TRANSFORMATION_HANDLER;

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new ScopedAliases(compiler, transformationHandler);
  }

  @Override
  public int getNumRepetitions() {
    return 1;
  }

  private void testScopedFailure(String code, DiagnosticType expectedError) {
    test("goog.scope(function() {" + code + "});",
        null, expectedError);
  }

  public void testFunctionDeclarationInScopeIsNonAliasLocal() {
    testScopedFailure("function f() {}",
        ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
  }

  public void testNonAliasLocal() {
    testScopedFailure("var x = 10", ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
    testScopedFailure("var x = goog.dom + 10",
        ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
    testScopedFailure("var x = goog['dom']",
        ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
    testScopedFailure("var x = goog.dom, y = 10",
        ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
    testScopedFailure("function f() {}",
        ScopedAliases.GOOG_SCOPE_NON_ALIAS_LOCAL);
  }
}