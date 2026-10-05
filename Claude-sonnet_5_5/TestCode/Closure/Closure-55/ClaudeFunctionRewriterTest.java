package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 538: FunctionRewriter must not try to
 * reduce getter/setter function expressions in object literals.
 */
public class ClaudeFunctionRewriterTest extends CompilerTestCase {

  public ClaudeFunctionRewriterTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new FunctionRewriter(compiler);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testIssue538() {
    String js =
        "/** @constructor */\n" +
        "WebInspector.Setting = function() {}\n" +
        "WebInspector.Setting.prototype = {\n" +
        "    get name0(){return this._name;},\n" +
        "    get name1(){return this._name;},\n" +
        "    get name2(){return this._name;},\n" +
        "    get name3(){return this._name;},\n" +
        "    get name4(){return this._name;},\n" +
        "    get name5(){return this._name;},\n" +
        "    get name6(){return this._name;},\n" +
        "    get name7(){return this._name;},\n" +
        "    get name8(){return this._name;},\n" +
        "    get name9(){return this._name;},\n" +
        "}";
    test(js, js);
  }
}