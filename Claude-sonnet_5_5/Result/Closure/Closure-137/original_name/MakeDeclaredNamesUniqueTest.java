package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

/**
 * Regression test for the contextual rename inverter in
 * {@link MakeDeclaredNamesUnique}.
 */
public class MakeDeclaredNamesUniqueTest extends CompilerTestCase {

  private boolean invert = false;

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    if (invert) {
      return MakeDeclaredNamesUnique.getContextualRenameInverter(compiler);
    } else {
      return new CompilerPass() {
        public void process(Node externs, Node root) {
          NodeTraversal.traverse(compiler, root, new MakeDeclaredNamesUnique());
        }
      };
    }
  }

  protected int getNumRepetitions() {
    return 1;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    invert = false;
  }

  public void testOnlyInversion3() {
    invert = true;
    test(
        "function x1() {" +
        "  var a$$1;" +
        "  function x2() {" +
        "    var a$$2;" +
        "  }" +
        "  function x3() {" +
        "    var a$$3;" +
        "  }" +
        "}",
        "function x1() {" +
        "  var a$$1;" +
        "  function x2() {" +
        "    var a;" +
        "  }" +
        "  function x3() {" +
        "    var a;" +
        "  }" +
        "}");
  }
}