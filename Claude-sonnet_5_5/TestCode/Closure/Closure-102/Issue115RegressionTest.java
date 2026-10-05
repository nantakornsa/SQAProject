package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for issue 115: a local variable named "arguments"
 * must have its redundant declaration removed before local names are
 * made unique.
 */
public class Issue115RegressionTest extends TestCase {

  public void testIssue115() {
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
    WarningLevel.VERBOSE.setOptionsForWarningLevel(options);

    Compiler compiler = new Compiler();

    JSSourceFile[] externs = new JSSourceFile[] {
        JSSourceFile.fromCode("externs",
            "function Array() {}\n" +
            "Array.prototype.slice = function(a, b) {};\n")
    };
    JSSourceFile[] inputs = new JSSourceFile[] {
        JSSourceFile.fromCode("input",
            "function f() { " +
            "  var arguments = Array.prototype.slice.call(arguments, 0);" +
            "  return arguments[0]; " +
            "}")
    };

    compiler.compile(externs, inputs, options);

    String output = compiler.toSource();
    assertNotNull(output);
    // The "var" declaration of "arguments" must be removed, leaving an
    // assignment to the built-in arguments object.
    assertFalse("Unexpected var declaration in output: " + output,
        output.contains("var "));
    assertTrue("Expected assignment to arguments in output: " + output,
        output.contains("arguments="));
  }
}