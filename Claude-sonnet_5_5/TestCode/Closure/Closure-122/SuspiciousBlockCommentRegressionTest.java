package com.google.javascript.jscomp.parsing;

import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.parsing.Config.LanguageMode;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.testing.TestErrorReporter;

import junit.framework.TestCase;

public class SuspiciousBlockCommentRegressionTest extends TestCase {

  private static final String SUSPICIOUS_COMMENT_WARNING =
      "Non-JSDoc comment has annotations. Did you mean to start it with '/**'?";

  private Node parse(String string, String... warnings) {
    TestErrorReporter testErrorReporter = new TestErrorReporter(null, warnings);
    Node script = null;
    try {
      script = ParserRunner.parse(
          SourceFile.fromCode("input", string),
          string,
          ParserRunner.createConfig(true, LanguageMode.ECMASCRIPT3, false, null),
          testErrorReporter).ast;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // verifying that all warnings were seen
    testErrorReporter.assertHasEncounteredAllWarnings();
    return script;
  }

  public void testSuspiciousBlockCommentWarningNoSpaceAfterStar() {
    parse("/* \n *@type {number} */ var x = 3;", SUSPICIOUS_COMMENT_WARNING);
  }

  public void testSuspiciousBlockCommentWarningTabIndent() {
    parse("/* \n\t* @type {number} */ var x = 3;", SUSPICIOUS_COMMENT_WARNING);
  }

  public void testSuspiciousBlockCommentWarningSpaceBeforeStar() {
    parse("/*\n   * @type {number} */ var x = 3;", SUSPICIOUS_COMMENT_WARNING);
  }

  public void testSuspiciousBlockCommentWarningNoSpaceAfterOpening() {
    parse("/*@type {number} */ var x = 3;", SUSPICIOUS_COMMENT_WARNING);
  }
}