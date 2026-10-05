package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

import junit.framework.TestCase;

/**
 * Regression test: object literal keys must not be counted as typeable
 * when computing the typed percentage.
 */
public class TypeCheckObjectLitKeyTypedPercentTest extends TestCase {

  private double getTypedPercent(String js) throws Exception {
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;

    compiler.init(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("input", js) },
        options);

    Node root = compiler.parseInputs();
    assertNotNull(root);
    Node externsRoot = root.getFirstChild();
    Node jsRoot = root.getLastChild();

    TypeCheck check = new TypeCheck(
        compiler,
        new SemanticReverseAbstractInterpreter(
            compiler.getCodingConvention(), compiler.getTypeRegistry()),
        compiler.getTypeRegistry(),
        CheckLevel.OFF,
        CheckLevel.OFF);
    check.processForTesting(externsRoot, jsRoot);
    return check.getTypedPercent();
  }

  public void testEnumObjectLiteralKeysAreNotTypeable() throws Exception {
    String js = "/** @enum {number} */ keys = {A: 1,B: 2,C: 3};";
    assertEquals(100.0, getTypedPercent(js), 0.1);
  }

  public void testObjectLiteralKeysAreNotTypeableInEnum() throws Exception {
    String js = "/** @enum {string} */ var e = {A: 'a', B: 'b'};";
    assertEquals(100.0, getTypedPercent(js), 0.1);
  }
}