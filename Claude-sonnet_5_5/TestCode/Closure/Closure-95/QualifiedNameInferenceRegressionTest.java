package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: a qualified name declared in a local scope but rooted in
 * a global variable should be declared in the global scope, so that its
 * declared type is visible from other scopes.
 */
public class QualifiedNameInferenceRegressionTest extends TestCase {

  public void testGlobalQualifiedNameDeclaredInLocalScope() throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
    WarningLevel.VERBOSE.setOptionsForWarningLevel(options);
    options.checkTypes = true;

    String js =
        "var ns = {}; " +
        "(function() { " +
        "    /** @param {number} x */ ns.foo = function(x) {}; })();" +
        "(function() { ns.foo(true); })();";

    JSSourceFile[] externs = new JSSourceFile[] {
        JSSourceFile.fromCode("externs", "")
    };
    JSSourceFile[] inputs = new JSSourceFile[] {
        JSSourceFile.fromCode("input", js)
    };

    Result result = compiler.compile(externs, inputs, options);

    boolean found = false;
    for (JSError warning : result.warnings) {
      if (warning.description.contains(
              "actual parameter 1 of ns.foo does not match formal parameter")
          && warning.description.contains("found   : boolean")
          && warning.description.contains("required: number")) {
        found = true;
      }
    }
    assertTrue("expected a type mismatch warning for ns.foo(true)", found);
  }
}