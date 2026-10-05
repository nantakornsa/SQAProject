package com.google.javascript.jscomp;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

/**
 * Regression test: a --define flag value wrapped in double quotes
 * must be treated as a string value.
 */
public class DefineFlagDoubleQuoteRegressionTest extends TestCase {

  public void testDefineFlagDoubleQuotedStringContainingSingleQuote()
      throws Exception {
    File jsFile = File.createTempFile("defineFlagTest", ".js");
    jsFile.deleteOnExit();
    FileWriter writer = new FileWriter(jsFile);
    try {
      writer.write("/** @define {string} */ var FOO = \"a\";");
    } finally {
      writer.close();
    }

    ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
    ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(outBytes);
    PrintStream err = new PrintStream(errBytes);

    String[] args = new String[] {
        "--compilation_level=SIMPLE_OPTIMIZATIONS",
        "--define=FOO=\"x'\"",
        "--js",
        jsFile.getAbsolutePath()
    };

    CommandLineRunner runner = new CommandLineRunner(args, out, err);
    int exitCode = runner.run();
    out.flush();
    err.flush();

    assertEquals("Compilation failed: " + errBytes.toString(), 0, exitCode);
    String output = outBytes.toString();
    assertTrue("Unexpected output: " + output,
        output.contains("var FOO=\"x'\""));
  }
}