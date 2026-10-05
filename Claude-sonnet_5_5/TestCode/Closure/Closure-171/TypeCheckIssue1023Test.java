package com.google.javascript.jscomp;

public class TypeCheckIssue1023Test extends TypeCheckTestCase {

  public void testIssue1023() throws Exception {
    testTypes(
        "/** @constructor */" +
        "function F() {}" +
        "(function () {" +
        "  F.prototype = {" +
        "    /** @param {string} x */" +
        "    bar: function (x) {  }" +
        "  };" +
        "})();" +
        "(new F()).bar(true)",
        "actual parameter 1 of F.prototype.bar does not match formal parameter\n" +
        "found   : boolean\n" +
        "required: string");
  }
}