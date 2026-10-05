package com.google.javascript.jscomp;

/**
 * Tests for {@link InlineObjectLiterals}.
 */
public class ClaudeInlineObjectLiteralsTest extends CompilerTestCase {

  public ClaudeInlineObjectLiteralsTest() {
    super("function alert() {}");
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
    test("function local() {" + js + "}",
         "function local() {" + expected + "}");
  }

  private void testSameLocal(String js) {
    testLocal(js, js);
  }

  public void testNoInlineDeletedProperties() {
    testSameLocal(
        "var foo = {bar:1};" +
        "delete foo.bar;" +
        "return foo.bar;");
  }
}