package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CheckLevel;

public class CheckGlobalNamesTest extends CompilerTestCase {

  private boolean injectNamespace = false;

  public CheckGlobalNamesTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    CheckGlobalNames checkGlobalNames =
        new CheckGlobalNames(compiler, CheckLevel.WARNING);
    if (injectNamespace) {
      return checkGlobalNames;
    }
    return checkGlobalNames;
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    injectNamespace = false;
  }

  public void testGlobalCatch() throws Exception {
    testSame(
        "try {" +
        "  throw Error();" +
        "} catch (e) {" +
        "  console.log(e.name)" +
        "}");
  }

  public void testGlobalCatchWithNestedFunction() throws Exception {
    testSame(
        "try {" +
        "  throw Error();" +
        "} catch (e) {" +
        "  var f = function() { return e.name; };" +
        "}");
  }
}