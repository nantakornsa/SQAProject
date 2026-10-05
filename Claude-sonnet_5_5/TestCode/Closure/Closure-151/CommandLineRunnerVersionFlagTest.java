package com.google.javascript.jscomp;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Regression test for the "--version" flag of the command line runner.
 */
public class CommandLineRunnerVersionFlagTest extends TestCase {

  public void testVersionFlag() {
    ByteArrayOutputStream outReader = new ByteArrayOutputStream();
    ByteArrayOutputStream errReader = new ByteArrayOutputStream();

    new CommandLineRunner(
        new String[] {"--version"},
        new PrintStream(outReader),
        new PrintStream(errReader));

    String err = new String(errReader.toByteArray());
    assertEquals(
        0,
        err.indexOf(
            "Closure Compiler (http://code.google.com/p/closure/compiler)\n" +
            "Version: "));
    assertTrue(err.contains("\nBuilt on:"));
  }
}