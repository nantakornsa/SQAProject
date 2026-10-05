package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 635: assigning a constructor to a
 * variable typed with a different constructor must report a type mismatch.
 * On the buggy version the mismatch is swallowed (registered silently), so
 * no warning is emitted.
 */
public class TypeCheckIssue635Test extends TypeCheckTest {

  public void testIssue635bRegression() throws Exception {
    testTypes(
        "/** @constructor */" +
        "function F() {}" +
        "/** @constructor */" +
        "function G() {}" +
        "/** @type {function(new:G)} */ var x = F;",
        "initializing variable\n" +
        "found   : function (new:F): undefined\n" +
        "required: function (new:G): ?");
  }
}