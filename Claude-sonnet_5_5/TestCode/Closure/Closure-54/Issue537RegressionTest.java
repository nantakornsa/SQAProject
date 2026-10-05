package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CheckLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.DiagnosticGroups;
import com.google.javascript.jscomp.JSSourceFile;

import junit.framework.TestCase;

/**
 * Regression test for issue 537: properties defined on an object-literal
 * prototype of a subclass must be visible, and inherited prototype
 * methods must be resolved through the declared superclass.
 */
public class Issue537RegressionTest extends TestCase {

  private static final String EXTERNS =
      "/** @constructor */ function Object() {}\n" +
      "/** @constructor */ function Function() {}\n" +
      "/**\n" +
      " * @param {...*} var_args\n" +
      " * @return {*}\n" +
      " */\n" +
      "Function.prototype.call = function(var_args) {};\n";

  private static boolean hasWarning(Compiler compiler, String description) {
    for (JSError warning : compiler.getWarnings()) {
      if (description.equals(warning.description)) {
        return true;
      }
    }
    return false;
  }

  public void testIssue537a() throws Exception {
    String js =
        "/** @constructor */ function Foo() {}" +
        "Foo.prototype = {method: function() {}};" +
        "/**\n" +
        " * @constructor\n" +
        " * @extends {Foo}\n" +
        " */\n" +
        "function Bar() {" +
        "  Foo.call(this);" +
        "  if (this.baz()) this.method(1);" +
        "}" +
        "Bar.prototype = {" +
        "  baz: function() {" +
        "    return true;" +
        "  }" +
        "};" +
        "Bar.prototype.__proto__ = Foo.prototype;";

    String expected =
        "Function Foo.prototype.method: called with 1 argument(s). " +
        "Function requires at least 0 argument(s) " +
        "and no more than 0 argument(s).";

    CompilerOptions options = new CompilerOptions();
    options.checkTypes = true;
    options.setWarningLevel(DiagnosticGroups.CHECK_TYPES, CheckLevel.WARNING);

    Compiler compiler = new Compiler();
    compiler.compile(
        new JSSourceFile[] { JSSourceFile.fromCode("externs", EXTERNS) },
        new JSSourceFile[] { JSSourceFile.fromCode("input", js) },
        options);

    assertTrue("Expected warning not found: " + expected,
        hasWarning(compiler, expected));
    assertFalse("Property baz should be defined on Bar",
        hasWarning(compiler, "Property baz never defined on Bar"));
  }
}