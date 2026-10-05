package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test for DELPROP handling in {@link NodeUtil}.
 */
public class NodeUtilDelPropTest extends TestCase {

  private static Node parse(String js) {
    Compiler compiler = new Compiler();
    Node n = compiler.parseTestCode(js);
    assertEquals(0, compiler.getErrorCount());
    return n;
  }

  private static Node getNode(String js) {
    Node root = parse("var a=(" + js + ");");
    Node var = root.getFirstChild();
    Node name = var.getFirstChild();
    return name.getFirstChild();
  }

  public void testIsBooleanResultDelete() {
    assertTrue(NodeUtil.isBooleanResult(getNode("delete a")));
    assertTrue(NodeUtil.isBooleanResult(getNode("delete a.b")));
    assertTrue(NodeUtil.isBooleanResult(getNode("delete a[0]")));
    assertFalse(NodeUtil.isBooleanResult(getNode("void 0")));
    assertFalse(NodeUtil.isBooleanResult(getNode("a.b")));
  }

  public void testEvaluatesToLocalValueDelete() {
    // Previously threw IllegalStateException("Unexpected expression node")
    assertTrue(NodeUtil.evaluatesToLocalValue(getNode("delete a.b")));
    assertTrue(NodeUtil.evaluatesToLocalValue(getNode("delete a")));
  }
}