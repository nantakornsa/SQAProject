package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test for Closure issue 728: boolean constants must be
 * estimated as having a cost of a single character.
 */
public class InlineCostEstimatorRegressionTest extends TestCase {

  public void testConstantsAreCheap() {
    checkCost("true", 1);
    checkCost("false", 1);
    checkCost("1", 1);
    checkCost("a ? true : false", "xx?1:1".length());
  }

  private void checkCost(String js, int expectedCost) {
    Compiler compiler = new Compiler();
    Node root = compiler.parseTestCode(js);
    assertEquals(expectedCost, InlineCostEstimator.getCost(root));
  }
}