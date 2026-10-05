package com.google.javascript.jscomp;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;

/**
 * Regression test: warning guard flags (--jscomp_error, --jscomp_warning,
 * --jscomp_off) must be applied in the order they appear on the command line.
 */
public class CommandLineRunnerWarningGuardOrderingTest extends TestCase {

  private int runWithFlags(String... guards) throws Exception {
    File input = File.createTempFile("warningGuardOrdering", ".js");
    input.deleteOnExit();
    Writer w = new FileWriter(input);
    try {
      w.write("function f() { this.a = 3; }");
    } finally {
      w.close();
    }

    String[] fixedArgs = new String[] {
        "--compilation_level=SIMPLE_OPTIMIZATIONS",
        "--warning_level=VERBOSE",
        "--js=" + input.getAbsolutePath()
    };
    String[] allArgs = new String[fixedArgs.length + guards.length];
    System.arraycopy(fixedArgs, 0, allArgs, 0, fixedArgs.length);
    System.arraycopy(guards, 0, allArgs, fixedArgs.length, guards.length);

    ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
    ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
    CommandLineRunner runner = new CommandLineRunner(
        allArgs, new PrintStream(outBytes), new PrintStream(errBytes));
    assertTrue(runner.shouldRunCompiler());
    return runner.doRun();
  }

  // The last flag (error) must win over the earlier one (off).
  public void testWarningGuardOrderingOffThenError() throws Exception {
    int exitCode = runWithFlags(
        "--jscomp_off=globalThis", "--jscomp_error=globalThis");
    assertTrue("Expected the global this use to be reported as an error",
        exitCode != 0);
  }

  // The last flag (off) must win over the earlier one (error).
  public void testWarningGuardOrderingErrorThenOff() throws Exception {
    int exitCode = runWithFlags(
        "--jscomp_error=globalThis", "--jscomp_off=globalThis");
    assertEquals(0, exitCode);
  }
}