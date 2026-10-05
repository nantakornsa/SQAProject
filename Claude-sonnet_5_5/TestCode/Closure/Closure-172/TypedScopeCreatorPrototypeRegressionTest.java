package com.google.javascript.jscomp;

import com.google.common.collect.ImmutableList;

import junit.framework.TestCase;

/**
 * Regression test for Issue 1024 / 1042: assigning to the "prototype"
 * property of a non-constructor object must not be treated as a declared
 * property.
 */
public class TypedScopeCreatorPrototypeRegressionTest extends TestCase {

  private Compiler compileWithTypeChecking(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    compiler.compile(
        ImmutableList.of(SourceFile.fromCode("externs", CompilerTestCase.DEFAULT_EXTERNS)),
        ImmutableList.of(SourceFile.fromCode("testcode", js)),
        options);
    return compiler;
  }

  public void testIssue1024() throws Exception {
    Compiler compiler = compileWithTypeChecking(
        "/** @param {Object} a */\n" +
        "function f(a) {\n" +
        "  a.prototype = '__proto'\n" +
        "}\n" +
        "/** @param {Object} b\n" +
        " *  @return {!Object}\n" +
        " */\n" +
        "function g(b) {\n" +
        "  return b.prototype\n" +
        "}\n");

    assertEquals("unexpected errors: " + java.util.Arrays.toString(compiler.getErrors()),
        0, compiler.getErrorCount());
    assertEquals(
        "unexpected warnings(s): " + java.util.Arrays.toString(compiler.getWarnings()),
        0, compiler.getWarningCount());
  }
}