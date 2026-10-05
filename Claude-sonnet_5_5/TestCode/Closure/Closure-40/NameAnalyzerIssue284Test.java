package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 284: NameAnalyzer crashed (or failed to
 * remove code) when a class-defining call such as goog.inherits referenced an
 * undeclared nested name.
 */
public class NameAnalyzerIssue284Test extends CompilerTestCase {

  public NameAnalyzerIssue284Test() {
    super("var window;");
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new NameAnalyzer(compiler, true);
  }

  public void testIssue284() {
    test(
        "var goog = {};" +
        "goog.inherits = function(x, y) {};" +
        "var ns = {};" +
        "/** @constructor */" +
        "ns.PageSelectionModel = function() {};" +
        "/** @constructor */" +
        "ns.PageSelectionModel.FooEvent = function() {};" +
        "/** @constructor */" +
        "ns.PageSelectionModel.SelectEvent = function() {};" +
        "goog.inherits(ns.PageSelectionModel.ChangeEvent," +
        "    ns.PageSelectionModel.FooEvent);",
        "");
  }
}