package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

public class ClaudeCodePrinterTest extends TestCase {

  private String parse(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    compiler.initOptions(options);
    Node n = compiler.parseTestCode(js);
    assertEquals(0, compiler.getErrorCount());
    CodePrinter.Builder builder = new CodePrinter.Builder(n);
    builder.setPrettyPrint(false);
    builder.setLineBreak(false);
    return builder.build();
  }

  private void assertPrint(String js, String expected) {
    assertEquals(expected, parse(js).trim().replaceAll(";$", ""));
  }

  public void testLabeledFunctionInIfSafariCompatibility() {
    assertPrint("if(e1)A:function goo(){return true}",
        "if(e1){A:function goo(){return true}}");
  }

  public void testLabeledDoLoopInIfIECompatibility() {
    assertPrint("if(x)A:do foo();while(y)",
        "if(x){A:do foo();while(y)}");
  }
}