package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class CheckUnreachableCodeTest extends CompilerTestCase {

  public CheckUnreachableCodeTest() {
    super("");
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new CheckUnreachableCode(compiler,
        CheckLevel.ERROR);
  }

  @Override
  public int getNumRepetitions() {
    return 1;
  }

  public void testInstanceOfThrowsException() {
    testSame("function f() {try { if (value instanceof type) return true; } " +
             "catch (e) { }}");
  }

  public void testInstanceOfThrowsExceptionWithCatchBody() {
    testSame("function f() {try { if (value instanceof type) return true; } " +
             "catch (e) { e; }}");
  }
}