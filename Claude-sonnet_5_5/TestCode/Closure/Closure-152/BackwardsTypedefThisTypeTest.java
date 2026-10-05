package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for backwards typedef use in a {@code @this} annotation,
 * which previously caused a ClassCastException during type resolution.
 */
public class BackwardsTypedefThisTypeTest extends TestCase {

  private void compileWithTypeChecking(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    JSSourceFile[] externs = new JSSourceFile[] {
        JSSourceFile.fromCode("externs", "")
    };
    JSSourceFile[] inputs = new JSSourceFile[] {
        JSSourceFile.fromCode("input", js)
    };

    // On the buggy version this throws a ClassCastException
    // (StringType cannot be cast to ObjectType).
    Result result = compiler.compile(externs, inputs, options);
    assertNotNull(result);
  }

  public void testBackwardsTypedefUse1() throws Exception {
    compileWithTypeChecking(
        "/** @this {MyTypedef} */ function f() {}" +
        "/** @typedef {string} */ var MyTypedef;");
  }
}