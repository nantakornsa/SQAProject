package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: an incomplete function in IDE mode must not crash the
 * compiler with an INTERNAL COMPILER ERROR.
 */
public class IncompleteFunctionIdeModeTest extends TestCase {

  public void testIncompleteFunction() {
    CompilerOptions options = new CompilerOptions();
    options.ideMode = true;
    CompilationLevel.WHITESPACE_ONLY.setOptionsForCompilationLevel(options);

    Compiler compiler = new Compiler();
    JSSourceFile extern = JSSourceFile.fromCode("externs", "");
    JSSourceFile input =
        JSSourceFile.fromCode("input", "var foo = {bar: function(e) }");

    try {
      compiler.compile(
          new JSSourceFile[] { extern },
          new JSSourceFile[] { input },
          options);
    } catch (RuntimeException e) {
      fail("Compiler crashed on incomplete function in IDE mode: " + e);
    }

    String output = compiler.toSource();
    assertNotNull(output);
    assertTrue("Unexpected output: " + output,
        output.contains("bar:function(e){}"));
  }
}