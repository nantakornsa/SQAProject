package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 378: InlineVariables must not inline
 * aliases of function parameters when the "arguments" object may have been
 * modified or escaped in the function.
 */
public class InlineVariablesTest extends CompilerTestCase {

  public InlineVariablesTest() {
    enableNormalize();
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new InlineVariables(
        compiler, InlineVariables.Mode.ALL, false);
  }

  public void testArgumentsModifiedInInnerFunction() {
    test(
      "function g(callback) {\n" +
      "  var f = callback;\n" +
      "  f.apply(this, arguments);\n" +
      "  function inner(callback) {" +
      "    var x = callback;\n" +
      "    arguments[0] = this;\n" +
      "    x.apply(this);\n" +
      "  }" +
      "}",
      "function g(callback) {\n" +
      "  callback.apply(this, arguments);\n" +
      "  function inner(callback) {" +
      "    var x = callback;\n" +
      "    arguments[0] = this;\n" +
      "    x.apply(this);\n" +
      "  }" +
      "}");
  }

  public void testIssue378ModifiedArguments1() {
    testSame(
      "function g(callback) {\n" +
      "  var f = callback;\n" +
      "  arguments[0] = this;\n" +
      "  f.apply(this, arguments);\n" +
      "}");
  }
}