package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 389: collapsing of aliased top-level
 * enums / names whose child names cannot be collapsed.
 */
public class CollapsePropertiesRegressionTest extends CompilerTestCase {

  public CollapsePropertiesRegressionTest() {
    super("", false);
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new CollapseProperties(compiler, true, true);
  }

  public void testAliasedTopLevelEnum() {
    test(
        "function alias() {}" +
        "var dojo = {};" +
        "dojo.gfx = {};" +
        "dojo.declare = function() {};" +
        "/** @enum {number} */" +
        "dojo.gfx.Shape = {SQUARE: 2};" +
        "dojo.gfx.Shape = dojo.declare('dojo.gfx.Shape');" +
        "alias(dojo);" +
        "alias(dojo.gfx.Shape.SQUARE);",
        "function alias() {}" +
        "var dojo = {};" +
        "dojo.gfx = {};" +
        "dojo.declare = function() {};" +
        "/** @constructor */" +
        "var dojo$gfx$Shape = {SQUARE: 2};" +
        "dojo$gfx$Shape = dojo.declare('dojo.gfx.Shape');" +
        "alias(dojo);" +
        "alias(dojo$gfx$Shape.SQUARE);",
        null,
        CollapseProperties.UNSAFE_NAMESPACE_WARNING);
  }
}