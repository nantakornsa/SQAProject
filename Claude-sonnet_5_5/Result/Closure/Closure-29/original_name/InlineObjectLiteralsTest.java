package com.google.javascript.jscomp;

public class InlineObjectLiteralsTest extends CompilerTestCase {

  public InlineObjectLiteralsTest() {
    super("function alert() {}");
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    return new InlineObjectLiterals(
        compiler, compiler.getUniqueNameIdSupplier());
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  private void testLocal(String js, String expected) {
    test("function f(){" + js + "}", "function f(){" + expected + "}");
  }

  private void testLocal(String js) {
    testLocal(js, js);
  }

  public void testIssue724() {
    // Reading a property that is not defined on the object literal
    // (e.g. one inherited from Object.prototype) must prevent inlining.
    testLocal(
        "var getType = {};" +
        "return functionToString.call(getType.toString);");
  }

  public void testUndefinedPropertyReadPreventsInlining() {
    testLocal(
        "var x = {}; var b = f(); x = {a:a, b:b}; if(x.a) g(x.b) + x.c");
  }
}