package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ClaudeFlowSensitiveInlineVariablesTest extends CompilerTestCase {

  public static final String EXTERNS =
      "var window; function someFunction() {}";

  public ClaudeFlowSensitiveInlineVariablesTest() {
    super(EXTERNS);
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node js) {
        (new MarkNoSideEffectCalls(compiler)).process(externs, js);
        (new FlowSensitiveInlineVariables(compiler)).process(externs, js);
      }
    };
  }

  private void noInline(String input) {
    inline(input, input);
  }

  private void inline(String input, String expected) {
    test("function _func() {" + input + "}",
         "function _func() {" + expected + "}");
  }

  public void testIssue794b() {
    noInline(
        "var x = 1; " +
        "try { x = x + someFunction(); } catch (e) {}" +
        "x = x + 1;" +
        "try { x = x + someFunction(); } catch (e) {}" +
        "return x;");
  }

  public void testIssue794bVariant() {
    noInline(
        "var x = 1; " +
        "try { x = x + someFunction(); } catch (e) {}" +
        "return x;");
  }
}