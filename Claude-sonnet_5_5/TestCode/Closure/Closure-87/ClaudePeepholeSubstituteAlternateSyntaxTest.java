package com.google.javascript.jscomp;

public class ClaudePeepholeSubstituteAlternateSyntaxTest extends CompilerTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public CompilerPass getProcessor(final Compiler compiler) {
    CompilerPass peepholePass =
        new PeepholeOptimizationsPass(compiler,
            new PeepholeSubstituteAlternateSyntax());
    return peepholePass;
  }

  public void testIssue291() {
    fold("if (true) { f.onchange(); }", "if (1) f.onchange();");
    foldSame("if (f) { f.onchange(); }");
    foldSame("if (f) { f.bar(); } else { f.onchange(); }");
    fold("if (f) { f.bonchange(); }", "f && f.bonchange();");
    foldSame("if (f) { f['x'](); }");
  }
}