package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Regression test for Closure issue 698: FlowSensitiveInlineVariables must
 * take dependencies on names not declared in the local scope into account,
 * and must traverse the externs as well as the root.
 */
public class FlowSensitiveInlineVariablesTest extends CompilerTestCase {

  public static final String EXTERN_FUNCTIONS = "" +
      "var print;\n" +
      "/** @nosideeffects */ function noSFX() {} \n" +
      "                      function hasSFX() {} \n";

  public FlowSensitiveInlineVariablesTest() {
    super(EXTERN_FUNCTIONS);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    enableNormalize();
  }

  @Override
  public int getNumRepetitions() {
    return 1;
  }

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

  private void noInline(String input) {
    inline(input, input);
  }

  private void inline(String input, String expected) {
    test(EXTERN_FUNCTIONS, input, expected);
  }

  public void testInlineAcrossSideEffect1() {
    // This can't be inlined because print() has side-effects and might change
    // the definition of noSFX.
    //
    // noSFX must be both const and pure in order to inline it.
    noInline("var y; var x = noSFX(y); print(x)");
  }
}