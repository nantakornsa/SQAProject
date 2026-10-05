package com.google.javascript.jscomp;

import com.google.common.collect.Lists;

import junit.framework.TestCase;

import java.util.List;

/**
 * Regression test for Issue 477: a malformed function type in JsDoc
 * ("@type function") followed directly by the end of the comment should only
 * report a missing opening paren, not an additional
 * "Unexpected end of file" warning.
 */
public class JsDocFunctionTypeEofRegressionTest extends TestCase {

  public void testIssue477() throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();

    List<JSSourceFile> externs = Lists.newArrayList(
        JSSourceFile.fromCode("externs", ""));
    List<JSSourceFile> inputs = Lists.newArrayList(
        JSSourceFile.fromCode("input", "/** @type function */\nvar x;"));

    compiler.compile(externs, inputs, options);

    boolean foundMissingParen = false;
    for (JSError warning : compiler.getWarnings()) {
      assertFalse("extra warning: " + warning.description,
          warning.description.contains("Unexpected end of file"));
      if (warning.description.contains("missing opening (")) {
        foundMissingParen = true;
      }
    }
    for (JSError error : compiler.getErrors()) {
      assertFalse("extra error: " + error.description,
          error.description.contains("Unexpected end of file"));
    }
    assertTrue("Expected 'missing opening (' warning", foundMissingParen);
  }
}