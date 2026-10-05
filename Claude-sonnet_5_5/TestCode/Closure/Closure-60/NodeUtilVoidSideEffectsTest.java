package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;
import com.google.javascript.jscomp.NodeUtil.ValueType;

import junit.framework.TestCase;

public class NodeUtilVoidSideEffectsTest extends TestCase {

  public void testPureBooleanValueOfVoidWithSideEffects() {
    // void foo()
    Node call = new Node(Token.CALL, Node.newString(Token.NAME, "foo"));
    Node voidNode = new Node(Token.VOID, call);

    // The operand has side effects, so the pure boolean value is unknown.
    assertEquals(
        com.google.javascript.rhino.jstype.TernaryValue.UNKNOWN,
        NodeUtil.getPureBooleanValue(voidNode));
  }

  public void testPureBooleanValueOfVoidWithoutSideEffects() {
    // void 0
    Node voidNode = new Node(Token.VOID, Node.newNumber(0));

    assertEquals(
        com.google.javascript.rhino.jstype.TernaryValue.FALSE,
        NodeUtil.getPureBooleanValue(voidNode));
  }
}