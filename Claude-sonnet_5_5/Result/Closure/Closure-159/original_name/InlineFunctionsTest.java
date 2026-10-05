package com.google.javascript.jscomp;

public class InlineFunctionsTest extends CompilerTestCase {
  boolean allowGlobalFunctionInlining = true;
  boolean allowBlockInlining = true;

  public InlineFunctionsTest() {
    super("", false);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    allowGlobalFunctionInlining = true;
    allowBlockInlining = true;
  }

  @Override
  protected CompilerPass getProcessor(final Compiler compiler) {
    compiler.resetUniqueNameId();
    return new InlineFunctions(
        compiler,
        compiler.getUniqueNameIdSupplier(),
        allowGlobalFunctionInlining,
        true, // allowLocalFunctionInlining
        allowBlockInlining);
  }

  public void testIssue423() {
    test(
        "(function($) {\n" +
        "  $.fn.multicheck = function(options) {\n" +
        "    initialize.call(this, options);\n" +
        "  };\n" +
        "\n" +
        "  function initialize(options) {\n" +
        "    options.checkboxes = $(this).siblings(':checkbox');\n" +
        "    preload_check_all.call(this);\n" +
        "  }\n" +
        "\n" +
        "  function preload_check_all() {\n" +
        "    $(this).data('checkboxes');\n" +
        "  }\n" +
        "})(jQuery)",
        "(function($){" +
        "  $.fn.multicheck=function(options$$1){" +
        "    {" +
        "     options$$1.checkboxes=$(this).siblings(\":checkbox\");" +
        "     {" +
        "       $(this).data(\"checkboxes\")" +
        "     }" +
        "    }" +
        "  }" +
        "})(jQuery)");
  }
}