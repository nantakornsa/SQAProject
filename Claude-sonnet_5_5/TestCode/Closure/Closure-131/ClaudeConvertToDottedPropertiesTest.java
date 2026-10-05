package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Tests for {@link ConvertToDottedProperties}.
 */
public class ClaudeConvertToDottedPropertiesTest extends CompilerTestCase {

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    return new ConvertToDottedProperties(compiler);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testConvertToDottedProp() {
    test("a['p']", "a.p");
    test("a['_p_']", "a._p_");
  }

  public void testQuotedPropsWithIgnorableCharacters() {
    // Keys containing identifier-ignorable characters must not be converted.
    testSame("({'':0})");
    testSame("({'1.0':0})");
    testSame("({'\u1d17A':0})");
    testSame("({'a\u0004b':0})");
  }

  public void testDoNotConvertIgnorableCharacters() {
    testSame("a['\u0004']");
    testSame("a['\u0004b']");
    testSame("a['b\u0004']");
    testSame("a['a\u0004b']");
  }
}