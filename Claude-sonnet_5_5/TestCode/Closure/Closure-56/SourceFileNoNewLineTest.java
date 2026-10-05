package com.google.javascript.jscomp;

import junit.framework.TestCase;

/**
 * Regression test: the last line of a source file that does not end with a
 * newline must still be returned by {@link SourceFile#getLine(int)}.
 */
public class SourceFileNoNewLineTest extends TestCase {

  public void testGetLineWithoutTrailingNewLine() throws Exception {
    SourceFile file = SourceFile.fromCode(
        "foo2", "foo2:first line\nfoo2:second line\nfoo2:third line");

    assertEquals("foo2:first line", file.getLine(1));
    assertEquals("foo2:second line", file.getLine(2));
    assertEquals("foo2:third line", file.getLine(3));
    assertNull(file.getLine(4));
  }

  public void testGetLineSingleLineWithoutNewLine() throws Exception {
    SourceFile file = SourceFile.fromCode("foo", "var x = 1;");

    assertEquals("var x = 1;", file.getLine(1));
    assertNull(file.getLine(2));
  }
}