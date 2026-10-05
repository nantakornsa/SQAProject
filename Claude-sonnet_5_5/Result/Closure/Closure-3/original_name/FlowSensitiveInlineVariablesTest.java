package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Regression test for Closure-3: a flow-sensitive inliner must not inline
 * expressions that reference a catch variable into code outside the catch block.
 */
public class FlowSensitiveInlineVariablesTest extends CompilerTestCase {

  public static final String EXTERN_FUNCTIONS = "var print;var alert;" +
      "function BAR(){}";

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node root) {
        (new MarkNoSideEffectCalls(compiler)).process(externs, root);
        (new FlowSensitiveInlineVariables(compiler)).process(externs, root);
      }
    };
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  private void noInline(String input) {
    inline(input, input);
  }

  private void inline(String input, String expected) {
    test(EXTERN_FUNCTIONS, "function _func() {" + input + "}",
        "function _func() {" + expected + "}", null, null);
  }

  public void testDoNotInlineCatchExpression1a() {
    noInline(
        "var a;\n" +
        "try {\n" +
        "  throw Error(\"\");\n" +
        "}catch(err) {" +
        "   a = err + 1;\n" +
        "}\n" +
        "return a.stack\n");
  }
}