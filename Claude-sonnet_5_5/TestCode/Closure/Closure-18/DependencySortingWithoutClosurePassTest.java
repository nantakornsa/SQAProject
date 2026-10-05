package com.google.javascript.jscomp;

/**
 * Regression test: dependency sorting must work even when the closure pass
 * is not enabled.
 */
public class DependencySortingWithoutClosurePassTest extends IntegrationTestCase {

  @Override
  protected CompilerOptions createCompilerOptions() {
    CompilerOptions options = new CompilerOptions();
    options.yieldSupported = true;
    return options;
  }

  public void testDependencySortingWithoutClosurePass() throws Exception {
    CompilerOptions options = createCompilerOptions();
    options.closurePass = false;
    options.setDependencyOptions(
        new DependencyOptions()
        .setDependencySorting(true));
    test(
        options,
        new String[] {
          "goog.require('x');",
          "goog.provide('x');",
        },
        new String[] {
          "goog.provide('x');",
          "goog.require('x');",

          // For complicated reasons involving modules,
          // the compiler creates a synthetic source file.
          "",
        });
  }
}