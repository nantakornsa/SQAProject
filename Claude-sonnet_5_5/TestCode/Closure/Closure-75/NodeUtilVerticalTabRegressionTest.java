package com.google.javascript.jscomp;

import com.google.javascript.rhino.jstype.TernaryValue;

import junit.framework.TestCase;

/**
 * Regression test for the IE vs. EcmaScript difference in treating the
 * vertical tab (\u000B) as whitespace when converting strings to numbers.
 */
public class NodeUtilVerticalTabRegressionTest extends TestCase {

  public void testIEStringVerticalTabNumberValue() {
    // IE does not treat vertical tab as whitespace, EcmaScript does, so the
    // numeric value of such a string must be unknown (null).
    assertNull(NodeUtil.getStringNumberValue("\u000b1"));
    assertNull(NodeUtil.getStringNumberValue("1\u000b"));
    assertNull(NodeUtil.getStringNumberValue("\u000b"));
  }

  public void testVerticalTabIsUnknownWhiteSpace() {
    assertEquals(TernaryValue.UNKNOWN, NodeUtil.isStrWhiteSpaceChar('\u000B'));
  }

  public void testRegularWhitespaceStillHandled() {
    assertEquals(Double.valueOf(1), NodeUtil.getStringNumberValue(" 1 "));
    assertEquals(TernaryValue.TRUE, NodeUtil.isStrWhiteSpaceChar(' '));
  }
}