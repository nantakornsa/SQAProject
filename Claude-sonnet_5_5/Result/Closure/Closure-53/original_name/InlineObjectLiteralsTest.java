package com.google.javascript.jscomp;

public class InlineObjectLiteralsTest extends CompilerTestCase {

  public InlineObjectLiteralsTest() {
    super("function _func(x) {}", false);
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new InlineObjectLiterals(
        compiler, compiler.getUniqueNameIdSupplier());
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  private void testLocal(String js, String expected) {
    test("function f() {" + js + "}",
         "function f() {" + expected + "}");
  }

  public void testBug545() {
    testLocal("var a = {}", "");
    testLocal("var a; a = {}", "true");
  }
}