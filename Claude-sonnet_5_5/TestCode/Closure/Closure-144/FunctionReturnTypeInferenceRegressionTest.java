package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;
import com.google.javascript.rhino.jstype.FunctionType;
import com.google.javascript.rhino.jstype.JSType;

import junit.framework.TestCase;

/**
 * Regression test: functions without return statements and without an
 * explicit {@code @return} annotation must have an inferred return type of
 * {@code undefined}, not the unknown type.
 */
public class FunctionReturnTypeInferenceRegressionTest extends TestCase {

  private Node firstFunction(Node n) {
    if (n.getType() == Token.FUNCTION) {
      return n;
    }
    for (Node c = n.getFirstChild(); c != null; c = c.getNext()) {
      Node result = firstFunction(c);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  private JSType compileAndGetFunctionType(String js) {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("testcode", js) },
        options);

    Node root = compiler.getRoot();
    assertNotNull(root);
    Node fn = firstFunction(root);
    assertNotNull("function node not found", fn);
    JSType type = fn.getJSType();
    assertNotNull("function has no type", type);
    return type;
  }

  public void testConstructorWithoutReturnHasVoidReturnType() {
    JSType type = compileAndGetFunctionType(
        "/** @constructor */ var Foo = function(){};");
    assertTrue(type instanceof FunctionType);
    assertEquals("function (this:Foo): undefined", type.toString());
  }

  public void testFunctionWithoutReturnHasVoidReturnType() {
    JSType type = compileAndGetFunctionType(
        "var f = function(x){ x = 1; };");
    assertTrue(type instanceof FunctionType);
    assertTrue(((FunctionType) type).getReturnType().isVoidType());
  }
}