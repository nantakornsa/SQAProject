package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: the output charset must be stored on the CompilerOptions
 * as a charset name (String) and be populated from the command line flags.
 */
public class CommandLineRunnerCharsetTest extends TestCase {

  private Compiler lastCompiler;

  private void compileWithArgs(String... args) {
    CommandLineRunner runner = new CommandLineRunner(args);
    Compiler compiler = runner.createCompiler();
    lastCompiler = compiler;
    CompilerOptions options = runner.createOptions();
    JSSourceFile[] externs = new JSSourceFile[] {
        JSSourceFile.fromCode("externs", "")
    };
    JSSourceFile[] inputs = new JSSourceFile[] {
        JSSourceFile.fromCode("input", "")
    };
    compiler.compile(externs, inputs, options);
  }

  public void testCharSetExpansion() {
    compileWithArgs();
    Object charset = lastCompiler.getOptions().outputCharset;
    assertEquals("US-ASCII", charset);

    compileWithArgs("--charset=UTF-8");
    charset = lastCompiler.getOptions().outputCharset;
    assertEquals("UTF-8", charset);
  }
}