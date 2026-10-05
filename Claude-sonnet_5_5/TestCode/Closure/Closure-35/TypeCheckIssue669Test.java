package com.google.javascript.jscomp;

public class TypeCheckIssue669Test extends TypeCheckTest {

  public void testIssue669Regression() throws Exception {
    testTypes(
        "/** @return {{prop1: (Object|undefined)}} */" +
        "function f(a) {" +
        "  var results;" +
        "  if (a) {" +
        "    results = {};" +
        "    results.prop1 = {a: 3};" +
        "  } else {" +
        "    results = {prop2: 3};" +
        "  }" +
        "  return results;" +
        "}");
  }
}