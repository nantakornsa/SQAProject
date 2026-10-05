package com.google.javascript.rhino;

import junit.framework.TestCase;

public class IRTest extends TestCase {

  public void testIssue727_1() {
    Node tryNode = IR.tryFinally(
        IR.block(),
        IR.block());

    assertTrue(tryNode.isTry());
    assertEquals(3, tryNode.getChildCount());
    for (Node child : tryNode.children()) {
      assertTrue(child.isBlock());
    }
    assertEquals(
        "TRY\n" +
        "    BLOCK\n" +
        "    BLOCK\n" +
        "    BLOCK\n",
        tryNode.toStringTree());
  }

  public void testIssue727_2() {
    Node tryNode = IR.tryCatch(
        IR.block(),
        IR.catchNode(IR.name("e"), IR.block()));

    assertTrue(tryNode.isTry());
    assertEquals(2, tryNode.getChildCount());
    assertTrue(tryNode.getFirstChild().isBlock());
    Node catchBlock = tryNode.getLastChild();
    assertTrue(catchBlock.isBlock());
    assertEquals(1, catchBlock.getChildCount());
    assertTrue(catchBlock.getFirstChild().isCatch());
  }
}