package com.google.javascript.jscomp;

import com.google.common.collect.Sets;
import com.google.javascript.jscomp.ExpressionDecomposer.DecompositionType;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;

import junit.framework.TestCase;

import java.util.Set;

/**
 * Regression test: an anonymous function expression used as the callee of
 * a call must not be treated as something that can be side-effected, so the
 * call argument is movable rather than needing decomposition.
 */
public class ExpressionDecomposerAnonymousFunctionRegressionTest
    extends TestCase {

  public void testCanExposeExpressionWithAnonymousFunctionCallee() {
    helperCanExposeExpression(
        DecompositionType.MOVABLE, "(function(a){b = a})(foo())", "foo");
  }

  public void testCanExposeExpressionSimpleCall() {
    helperCanExposeExpression(
        DecompositionType.MOVABLE, "x = foo()", "foo");
  }

  private void helperCanExposeExpression(
      DecompositionType expectedResult, String code, String fnName) {
    Compiler compiler = getCompiler();
    Set<String> knownConstants = Sets.newHashSet();
    ExpressionDecomposer decomposer = new ExpressionDecomposer(
        compiler, compiler.getUniqueNameIdSupplier(), knownConstants);
    decomposer.setTempNamePrefix("temp");
    decomposer.setResultNamePrefix("result");

    Node tree = compiler.parseTestCode(code);
    assertNotNull(tree);
    assertEquals(0, compiler.getErrorCount());

    Node callSite = findCall(tree, fnName);
    assertNotNull("Call to " + fnName + " was not found", callSite);

    compiler.resetUniqueNameId();
    DecompositionType result = decomposer.canExposeExpression(callSite);
    assertEquals(expectedResult, result);
  }

  private static Compiler getCompiler() {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.setCodingConvention(new GoogleCodingConvention());
    compiler.initOptions(options);
    return compiler;
  }

  private static Node findCall(Node n, String name) {
    if (n.getType() == Token.CALL) {
      Node callee = n.getFirstChild();
      if (callee != null && callee.getType() == Token.NAME
          && name.equals(callee.getString())) {
        return n;
      }
    }
    for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
      Node result = findCall(c, name);
      if (result != null) {
        return result;
      }
    }
    return null;
  }
}