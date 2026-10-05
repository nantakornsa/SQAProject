package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test for LightweightMessageFormatter: the caret must be printed
 * when the error position is at the very end of the source line.
 */
public class LightweightMessageFormatterTest extends TestCase {

  private static final DiagnosticType FOO_TYPE =
      DiagnosticType.error("TEST_FOO", "error description here");

  public void testFormatErrorSpaceEndOfLine1() throws Exception {
    JSError error = JSError.make("javascript/complex.js",
        1, 10, FOO_TYPE);
    LightweightMessageFormatter formatter = formatter("assert (1;");
    assertEquals("javascript/complex.js:1: ERROR - error description here\n" +
        "assert (1;\n" +
        "          ^\n", formatter.formatError(error));
  }

  public void testFormatErrorSpaceEndOfLine2() throws Exception {
    JSError error = JSError.make("javascript/complex.js",
        1, 6, FOO_TYPE);
    LightweightMessageFormatter formatter = formatter("assert;");
    assertEquals("javascript/complex.js:1: ERROR - error description here\n" +
        "assert;\n" +
        "      ^\n", formatter.formatError(error));
  }

  private LightweightMessageFormatter formatter(String string) {
    return new LightweightMessageFormatter(source(string));
  }

  private SourceExcerptProvider source(final String source) {
    return new SourceExcerptProvider() {
      public String getSourceLine(String sourceName, int lineNumber) {
        return source;
      }

      public Region getSourceRegion(String sourceName, int lineNumber) {
        return null;
      }
    };
  }
}