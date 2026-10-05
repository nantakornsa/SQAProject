package com.google.javascript.jscomp;

import junit.framework.TestCase;

import com.google.javascript.rhino.Node;

/**
 * Regression test for Closure issue 787: PeepholeOptimizationsPass fetched
 * the next sibling after traversing the current node, so it could skip or
 * mishandle nodes that were removed or replaced during traversal.
 */
public class PeepholeTraversalIssue787Test extends TestCase {

  public void testIssue787() {
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
    WarningLevel.DEFAULT.setOptionsForWarningLevel(options);

    String code = "" +
        "function some_function() {\n" +
        "  var fn1;\n" +
        "  var fn2;\n" +
        "\n" +
        "  if (any_expression) {\n" +
        "    fn2 = external_ref;\n" +
        "    fn1 = function (content) {\n" +
        "      return fn2();\n" +
        "    }\n" +
        "  }\n" +
        "\n" +
        "  return {\n" +
        "    method1: function () {\n" +
        "      if (fn1) fn1();\n" +
        "      return true;\n" +
        "    },\n" +
        "    method2: function () {\n" +
        "      return false;\n" +
        "    }\n" +
        "  }\n" +
        "}";

    String expected = "" +
        "function some_function() {\n" +
        "  var a, b;\n" +
        "  any_expression && (b = external_ref, a = function() {\n" +
        "    return b()\n" +
        "  });\n" +
        "  return{method1:function() {\n" +
        "    a && a();\n" +
        "    return !0\n" +
        "  }, method2:function() {\n" +
        "    return !1\n" +
        "  }}\n" +
        "}\n";

    Compiler compiler = new Compiler();
    Result result = compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", "") },
        new JSSourceFile[] { JSSourceFile.fromCode("input", code) },
        options);
    assertTrue("Compilation should succeed", result.success);
    String actual = compiler.toSource();

    Compiler expectedCompiler = new Compiler();
    expectedCompiler.initOptions(options);
    Node expectedRoot = expectedCompiler.parseTestCode(expected);
    assertNotNull(expectedRoot);
    String expectedSource = expectedCompiler.toSource(expectedRoot);

    assertEquals(expectedSource, actual);
  }
}