package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for var_args parameter type checking (Closure-96).
 */
public class TypeCheckVarArgsRegressionTest extends TestCase {

  public void testFunctionArguments16() throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] {
            JSSourceFile.fromCode(
                "input",
                "/** @param {...number} var_args */" +
                "function g(var_args) {} g(1, true);")
        },
        options);

    JSError[] warnings = compiler.getWarnings();
    assertEquals("expected a warning", 1, warnings.length);
    String description = warnings[0].description;
    assertTrue(description,
        description.contains(
            "actual parameter 2 of g does not match formal parameter"));
    assertTrue(description, description.contains("found   : boolean"));
    assertTrue(description,
        description.contains("required: (number|undefined)"));
  }
}