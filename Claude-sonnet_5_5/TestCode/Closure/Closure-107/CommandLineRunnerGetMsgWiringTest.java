package com.google.javascript.jscomp;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;

/**
 * Regression test: goog.getMsg wiring in ADVANCED mode without a message
 * bundle must not produce i18n (message convention) warnings.
 */
public class CommandLineRunnerGetMsgWiringTest extends TestCase {

  public void testGetMsgWiringNoWarnings() throws Exception {
    File input = File.createTempFile("getMsgWiring", ".js");
    input.deleteOnExit();
    Writer writer = new FileWriter(input);
    try {
      writer.write("/** @desc A bad foo. */ var MSG_FOO = 1;");
    } finally {
      writer.close();
    }

    String[] args = new String[] {
        "--compilation_level=ADVANCED_OPTIMIZATIONS",
        "--js", input.getAbsolutePath()
    };

    ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
    ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
    CommandLineRunner runner = new CommandLineRunner(
        args, new PrintStream(outBytes), new PrintStream(errBytes));
    Compiler compiler = runner.getCompiler();

    assertTrue(runner.shouldRunCompiler());
    int exitCode = runner.doRun();

    assertEquals(0, exitCode);
    assertEquals("Expected no errors", 0, compiler.getErrors().length);
    assertEquals("Expected no warnings", 0, compiler.getWarnings().length);
  }
}