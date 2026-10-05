package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: modules without exports must not get a
 * "module$exports" override emitted by ProcessCommonJSModules.
 */
public class ProcessCommonJSModulesNoExportsRegressionTest extends TestCase {

  private String compile(String filename, String code) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.processCommonJSModules = true;

    Result result = compiler.compile(
        JSSourceFile.fromCode("externs.js", ""),
        JSSourceFile.fromCode(filename, code),
        options);
    assertTrue("Compilation should succeed", result.success);
    return compiler.toSource();
  }

  public void testModuleWithoutExportsHasNoModuleExportsOverride() {
    String output = compile("test.js",
        "var name = require('other'); name.call(); new name;");
    assertFalse(
        "Unexpected module$exports override in output: " + output,
        output.contains("module$exports"));
  }

  public void testModuleWithoutExportsStillDeclaresModule() {
    String output = compile("test.js", "var x = 1;");
    assertTrue("Module object should still be declared: " + output,
        output.contains("module$test"));
    assertFalse(
        "Unexpected module$exports override in output: " + output,
        output.contains("module$exports"));
  }
}