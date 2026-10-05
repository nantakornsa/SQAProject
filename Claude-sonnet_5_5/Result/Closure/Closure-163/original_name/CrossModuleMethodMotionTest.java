package com.google.javascript.jscomp;

/**
 * Tests for {@link CrossModuleMethodMotion}.
 */
public class CrossModuleMethodMotionTest extends CompilerTestCase {

  private static final String EXTERNS =
      "IFoo.prototype.bar; var mExtern; mExtern.bExtern; mExtern['cExtern'];";

  public CrossModuleMethodMotionTest() {
    super(EXTERNS);
  }

  @Override
  public CompilerPass getProcessor(Compiler compiler) {
    return new CrossModuleMethodMotion(
        compiler,
        new CrossModuleMethodMotion.IdGenerator() {
          private int counter = 0;

          @Override
          public String generateNextId() {
            return "" + (counter++);
          }
        },
        false);
  }

  public void testIssue600b() {
    testSame(
        createModuleChain(
            "var jQuery1 = (function() {\n" +
            "  var jQuery2 = function() {};\n" +
            "  jQuery2.prototype = {\n" +
            "    size: function() {\n" +
            "      return 1;\n" +
            "    }\n" +
            "  };\n" +
            "  return jQuery2;\n" +
            "})();\n",

            "(function() {" +
            "  var div = jQuery1('div');" +
            "  div.size();" +
            "})();"));
  }
}