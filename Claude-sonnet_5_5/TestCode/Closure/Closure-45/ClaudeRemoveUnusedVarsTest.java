package com.google.javascript.jscomp;

public class ClaudeRemoveUnusedVarsTest extends CompilerTestCase {
  private boolean removeGlobal = true;
  private boolean preserveFunctionExpressionNames = false;

  public ClaudeRemoveUnusedVarsTest() {
    super("function alert() {}");
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    removeGlobal = true;
    preserveFunctionExpressionNames = false;
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      @Override
      public void process(Node externs, Node root) {
        new RemoveUnusedVars(compiler, removeGlobal,
            preserveFunctionExpressionNames).process(externs, root);
      }
    };
  }

  public void testIssue618_1() {
    this.removeGlobal = false;
    testSame(
        "function f() {\n" +
        "  var a = [], b;\n" +
        "  a.push(b = []);\n" +
        "  b[0] = 1;\n" +
        "  return a;\n" +
        "}");
  }

  public void testIssue618_2() {
    this.removeGlobal = false;
    testSame(
        "function f() {\n" +
        "  var a = [], b;\n" +
        "  a.push(b = {});\n" +
        "  b.x = 1;\n" +
        "  return a;\n" +
        "}");
  }
}