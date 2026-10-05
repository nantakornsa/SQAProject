package com.google.javascript.jscomp;

import com.google.javascript.jscomp.type.ClosureReverseAbstractInterpreter;
import com.google.javascript.jscomp.type.FlowScope;
import com.google.javascript.jscomp.type.ReverseAbstractInterpreter;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.jstype.JSType;
import com.google.javascript.rhino.jstype.JSTypeNative;

/**
 * Regression test for goog.isFunction type refinement on a union type that
 * contains the generic Object type (Closure-7).
 */
public class ClosureReverseAbstractInterpreterFunctionRegressionTest
    extends CompilerTypeTestCase {

  public void testGoogIsFunctionOnObjectUnion() throws Exception {
    JSType unionType = registry.createUnionType(
        JSTypeNative.OBJECT_TYPE,
        JSTypeNative.NUMBER_TYPE,
        JSTypeNative.STRING_TYPE,
        JSTypeNative.BOOLEAN_TYPE);
    JSType ctorType = registry.getNativeType(JSTypeNative.U2U_CONSTRUCTOR_TYPE);

    // function(a) where a : (Object|number|string|boolean)
    Node n = compiler.parseTestCode("var a; goog.isFunction(a)");
    Node call = n.getLastChild().getFirstChild();

    Scope scope = new SyntacticScopeCreator(compiler).createScope(n, null);
    FlowScope flowScope = LinkedFlowScope.createEntryLattice(scope);
    flowScope.inferSlotType("a", unionType);

    ReverseAbstractInterpreter rai =
        new ClosureReverseAbstractInterpreter(registry);

    // true outcome: the Object part must be restricted to the function type
    FlowScope trueScope =
        rai.getPreciserScopeKnowingConditionOutcome(call, flowScope, true);
    assertEquals(ctorType, trueScope.getSlot("a").getType());

    // false outcome: type should be left unchanged
    FlowScope falseScope =
        rai.getPreciserScopeKnowingConditionOutcome(call, flowScope, false);
    assertEquals(unionType, falseScope.getSlot("a").getType());
  }
}