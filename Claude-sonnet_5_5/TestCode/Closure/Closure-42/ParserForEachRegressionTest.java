package com.google.javascript.jscomp.parsing;

import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.JSSourceFile;

import junit.framework.TestCase;

public class ParserForEachRegressionTest extends TestCase {

  public void testForEachUnsupported() {
    String code =
        "function f(stamp, status) {\n" +
        "  for each ( var curTiming in this.timeLog.timings ) {\n" +
        "    if ( curTiming.callId == stamp ) {\n" +
        "      curTiming.flag = status;\n" +
        "      break;\n" +
        "    }\n" +
        "  }\n" +
        "};";

    Compiler compiler = new Compiler();
    compiler.initOptions(new CompilerOptions());
    compiler.parse(JSSourceFile.fromCode("testcode", code));

    JSError[] errors = compiler.getErrors();
    assertTrue("Expected a parse error for 'for each'", errors.length > 0);

    boolean found = false;
    for (JSError error : errors) {
      if (error.description.contains(
          "unsupported language extension: for each")) {
        found = true;
      }
    }
    assertTrue("Expected 'unsupported language extension: for each' error",
        found);
  }
}