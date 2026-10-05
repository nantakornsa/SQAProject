package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for issue 668: singleton getter calls must not be
 * inlined by InlineVariables, since that confuses class removal logic.
 */
public class SingletonGetterInlineRegressionTest extends TestCase {

  public void testSingletonGetter1() {
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.ADVANCED_OPTIMIZATIONS
        .setOptionsForCompilationLevel(options);
    options.setCodingConvention(new ClosureCodingConvention());

    String input =
        "/** @const */\n" +
        "var goog = goog || {};\n" +
        "goog.addSingletonGetter = function(ctor) {\n" +
        "  ctor.getInstance = function() {\n" +
        "    return ctor.instance_ || (ctor.instance_ = new ctor());\n" +
        "  };\n" +
        "};" +
        "function Foo() {}\n" +
        "goog.addSingletonGetter(Foo);" +
        "Foo.prototype.bar = 1;" +
        "function Bar() {}\n" +
        "goog.addSingletonGetter(Bar);" +
        "Bar.prototype.bar = 1;";

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        JSSourceFile.fromCode("externs", ""),
        JSSourceFile.fromCode("input", input),
        options);

    assertTrue(result.success);
    assertEquals("", compiler.toSource());
  }
}