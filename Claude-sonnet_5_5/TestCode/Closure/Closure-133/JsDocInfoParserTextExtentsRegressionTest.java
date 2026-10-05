package com.google.javascript.jscomp.parsing;

import com.google.javascript.jscomp.parsing.Config.LanguageMode;
import com.google.javascript.jscomp.parsing.ParserRunner.ParseResult;
import com.google.javascript.rhino.ErrorReporter;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.SimpleSourceFile;

import junit.framework.TestCase;

/**
 * Regression test: after reading the remaining JSDoc line, a previously
 * unread token must be discarded. Otherwise bad position information
 * is recorded for the JSDoc text extents.
 */
public class JsDocInfoParserTextExtentsRegressionTest extends TestCase {

  private static class QuietErrorReporter implements ErrorReporter {
    @Override
    public void warning(String message, String sourceName, int line,
        int lineOffset) {
      // Warnings are expected (malformed type annotation); ignore them.
    }

    @Override
    public void error(String message, String sourceName, int line,
        int lineOffset) {
      // Ignore.
    }
  }

  public void testTextExtents() {
    String code =
        "/** @return {@code foo} bar \n *    baz. */ function f() {}";
    ParseResult result = ParserRunner.parse(
        new SimpleSourceFile("testcode", false),
        code,
        ParserRunner.createConfig(true, LanguageMode.ECMASCRIPT3, null),
        new QuietErrorReporter());
    Node script = result.ast;
    assertNotNull(script);
  }
}