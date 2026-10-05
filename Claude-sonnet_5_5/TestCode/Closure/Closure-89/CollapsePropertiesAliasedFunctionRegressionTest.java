package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: properties of an aliased function must not be collapsed,
 * even when they are modified from a local scope.
 */
public class CollapsePropertiesAliasedFunctionRegressionTest extends TestCase {

  private static final String EXTERNS = "var window;";

  private String compile(String js, boolean collapse) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.collapseProperties = collapse;
    compiler.compile(
        JSSourceFile.fromCode("externs", EXTERNS),
        JSSourceFile.fromCode("input", js),
        options);
    return compiler.toSource();
  }

  public void testAddPropertyToChildOfUncollapsibleFunctionInLocalScope() {
    String js = "function a() {} a.b = {x: 0}; var c = a;" +
        "(function() {a.b.y = 0;})(); a.b.y;";
    String expected = compile(js, false);
    String actual = compile(js, true);
    assertEquals(expected, actual);
  }
}