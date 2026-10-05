package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * Regression test: function declarations (hoisted or not) inside goog.scope
 * must be rewritten instead of reporting JSC_GOOG_SCOPE_NON_ALIAS_LOCAL.
 */
public class ScopedAliasesFunctionDeclarationRegressionTest extends TestCase {

  private Compiler compileScoped(String code) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.closurePass = true;

    List<SourceFile> externs = Arrays.asList(
        SourceFile.fromCode("externs.js", "var window; function g(x) {}"));
    List<SourceFile> inputs = Arrays.asList(
        SourceFile.fromCode("testcode", code));

    compiler.compile(externs, inputs, options);
    return compiler;
  }

  public void testFunctionDeclarationInGoogScope() {
    Compiler compiler = compileScoped(
        "goog.scope(function() { function f() {} });");
    assertEquals("Unexpected errors: " + Arrays.toString(compiler.getErrors()),
        0, compiler.getErrorCount());
    String output = compiler.toSource();
    assertTrue(output, output.contains("$jscomp.scope.f"));
  }

  public void testHoistedFunctionDeclarationInGoogScope() {
    Compiler compiler = compileScoped(
        "goog.scope(function() { g(f); function f() {} });");
    assertEquals("Unexpected errors: " + Arrays.toString(compiler.getErrors()),
        0, compiler.getErrorCount());
    String output = compiler.toSource();
    assertTrue(output, output.contains("$jscomp.scope.f"));
  }
}