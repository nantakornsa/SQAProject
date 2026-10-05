package com.google.javascript.jscomp;

import com.google.javascript.jscomp.NodeTraversal.AbstractPostOrderCallback;
import com.google.javascript.jscomp.NodeTraversal.Callback;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.jstype.JSType;
import com.google.javascript.rhino.jstype.ObjectType;

public class ClaudeTypedScopeCreatorTest extends CompilerTestCase {

  private Scope globalScope;
  private Scope lastLocalScope;

  @Override
  public int getNumRepetitions() {
    return 1;
  }

  private final Callback callback = new AbstractPostOrderCallback() {
    public void visit(NodeTraversal t, Node n, Node parent) {
      Scope s = t.getScope();
      if (s.isGlobal()) {
        globalScope = s;
      } else {
        lastLocalScope = s;
      }
    }
  };

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      public void process(Node externs, Node root) {
        MemoizedScopeCreator scopeCreator =
            new MemoizedScopeCreator(new TypedScopeCreator(compiler));
        Scope topScope = scopeCreator.createScope(root.getParent(), null);
        (new TypeInferencePass(
            compiler, compiler.getReverseAbstractInterpreter(),
            topScope, scopeCreator)).process(externs, root);
        NodeTraversal t = new NodeTraversal(
            compiler, callback, scopeCreator);
        t.traverseRoots(externs, root);
      }
    };
  }

  private JSType findNameType(String name, Scope scope) {
    return scope.getVar(name).getType();
  }

  public void testNamespacedFunctionStubLocal() {
    testSame(
        "(function() {" +
        "var goog = {};" +
        "/** @param {number} x */ goog.foo;" +
        "});");

    ObjectType goog = (ObjectType) findNameType("goog", lastLocalScope);
    assertTrue(goog.hasProperty("foo"));
    assertEquals("function (number): ?",
        goog.getPropertyType("foo").toString());
    assertTrue(goog.isPropertyTypeDeclared("foo"));

    assertEquals(lastLocalScope.getVar("goog.foo").getType(),
        goog.getPropertyType("foo"));
  }

  public void testCollectedFunctionStubLocal() {
    testSame(
        "(function() {" +
        "  /** @constructor */ function f() {}" +
        "  /** @param {number} x */ f.prototype.foo;" +
        "});");

    ObjectType f = (ObjectType) findNameType("f", lastLocalScope);
    ObjectType proto = f.getPropertyType("prototype").toObjectType();
    assertTrue(proto.hasProperty("foo"));
    assertEquals("function (number): ?",
        proto.getPropertyType("foo").toString());
    assertTrue(proto.isPropertyTypeDeclared("foo"));
  }
}