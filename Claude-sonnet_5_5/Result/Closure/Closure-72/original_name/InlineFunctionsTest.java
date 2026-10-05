package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class InlineFunctionsTest extends CompilerTestCase {
  boolean allowGlobalFunctionInlining = true;
  boolean allowBlockInlining = true;
  final boolean allowExpressionDecomposition = true;
  final boolean allowFunctionExpressionInlining = true;
  final boolean allowLocalFunctionInlining = true;

  public InlineFunctionsTest() {
    this.enableNormalize();
    this.enableMarkNoSideEffects();
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    compiler.resetUniqueNameId();
    return new InlineFunctions(
        compiler,
        compiler.getUniqueNameIdSupplier(),
        allowGlobalFunctionInlining,
        allowLocalFunctionInlining,
        allowBlockInlining);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    allowGlobalFunctionInlining = true;
    allowBlockInlining = true;
  }

  public void testInlineFunctions31() {
    // Don't introduce a duplicate label in the same scope
    test("function foo(){ lab:{4;} }" +
        "lab:{foo();}",
        "lab:{{JSCompiler_inline_label_0:{4}}}");
  }
}