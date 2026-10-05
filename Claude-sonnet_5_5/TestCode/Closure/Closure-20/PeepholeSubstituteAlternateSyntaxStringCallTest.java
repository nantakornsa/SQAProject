package com.google.javascript.jscomp;

/**
 * Regression test for Closure issue 759: {@code String(x)} must only be
 * rewritten to {@code '' + x} when x is a single immutable value.
 */
public class PeepholeSubstituteAlternateSyntaxStringCallTest
    extends CompilerTestCase {

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    return new PeepholeOptimizationsPass(compiler,
        new PeepholeSubstituteAlternateSyntax(true));
  }

  public void testStringCallWithObjectLiteralIsNotReplaced() {
    // '' + {valueOf: ...} has different semantics than String({valueOf: ...}),
    // so the call must be left alone.
    testSame("var a = String({valueOf: function() { return 1; }});");
  }

  public void testStringCallWithMultipleArgsIsNotReplaced() {
    testSame("var a = String('hello', bar());");
  }
}