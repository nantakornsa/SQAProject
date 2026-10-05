package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test for Closure-86: a "new" expression must not be treated as
 * evaluating to a local value, because the constructor may have aliased the
 * new object.
 */
public class NodeUtilNewLocalValueTest extends TestCase {

  private static Node parse(String js) {
    Compiler compiler = new Compiler();
    Node n = compiler.parseTestCode(js);
    assertEquals(0, compiler.getErrorCount());
    return n;
  }

  private boolean testLocalValue(String js) {
    Node script = parse("var test = " + js + ";");
    Node var = script.getFirstChild();
    assertEquals(com.google.javascript.rhino.Token.VAR, var.getType());
    Node name = var.getFirstChild();
    Node init = name.getFirstChild();
    return NodeUtil.evaluatesToLocalValue(init);
  }

  public void testNewExpressionIsNotLocalValue() throws Exception {
    // We can't know if new objects are local unless we know
    // that they don't alias themselves.
    assertFalse(testLocalValue("new x()"));
    assertFalse(testLocalValue("new x"));
    assertFalse(testLocalValue("new x(1, 2)"));

    // property references are assumed to be non-local
    assertFalse(testLocalValue("(new x()).y"));
    assertFalse(testLocalValue("(new x())['y']"));

    // Literals remain local.
    assertTrue(testLocalValue("[]"));
    assertTrue(testLocalValue("{}"));
    assertTrue(testLocalValue("1"));
  }
}