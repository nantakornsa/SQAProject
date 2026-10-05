package com.google.javascript.jscomp;

import com.google.javascript.rhino.Node;

public class ClaudeFoldConstantsTest extends CompilerTestCase {

  public ClaudeFoldConstantsTest() {
    super("", false);
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    return new CompilerPass() {
      public void process(Node externs, Node js) {
        NodeTraversal.traverse(compiler, js, new FoldConstants(compiler));
      }
    };
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  void foldSame(String js) {
    testSame(js);
  }

  void fold(String js, String expected) {
    test(js, expected);
  }

  public void testStringJoinAddEmptyStrings() {
    fold("x = ['a', 'b', 'c'].join('')", "x = \"abc\"");
    fold("x = [].join(',')", "x = \"\"");
    fold("x = ['a', 'b', 'c'].join(',')", "x = \"a,b,c\"");

    // Leading empty strings must still be joined with the separator.
    foldSame("x = ['', foo].join(',')");
    foldSame("x = ['', foo, ''].join(',')");

    fold("x = ['', '', foo, ''].join(',')", "x = [',', foo, ''].join(',')");
    fold("x = ['', '', foo, '', ''].join(',')",
         "x = [',', foo, ','].join(',')");
    fold("x = ['', '', foo, '', '', bar].join(',')",
         "x = [',', foo, ',', bar].join(',')");

    fold("x = [1,2,3].join('abcdef')", "x = '1abcdef2abcdef3'");
  }
}