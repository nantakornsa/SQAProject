package com.google.javascript.jscomp;

import com.google.common.collect.ImmutableList;

import junit.framework.TestCase;

/**
 * Regression test: an unprovided goog.require must be removed (and reported
 * as MISSING_PROVIDE_ERROR) by ProcessClosurePrimitives when the requires
 * check is on, so that VarCheck does not additionally report "goog" as an
 * undeclared variable.
 */
public class VarCheckClosurePassRegressionTest extends TestCase {

  public void testNoUndeclaredVarWhenUsingClosurePass() {
    CompilerOptions options = new CompilerOptions();
    options.setClosurePass(true);
    options.setCheckSymbols(true);
    options.setBrokenClosureRequiresLevel(CheckLevel.ERROR);

    Compiler compiler = new Compiler();
    compiler.compile(
        ImmutableList.of(SourceFile.fromCode("externs",
            "var window; function alert() {}")),
        ImmutableList.of(SourceFile.fromCode("input",
            "goog.require('namespace.Class1');\n")),
        options);

    JSError[] errors = compiler.getErrors();
    assertEquals("There should be one error.", 1, errors.length);
    assertEquals(ProcessClosurePrimitives.MISSING_PROVIDE_ERROR,
        errors[0].getType());
  }
}