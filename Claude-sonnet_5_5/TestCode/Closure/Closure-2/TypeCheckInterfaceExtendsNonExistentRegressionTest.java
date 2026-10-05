package com.google.javascript.jscomp;

public class TypeCheckInterfaceExtendsNonExistentRegressionTest
    extends TypeCheckTest {

  public void testRegressionInterfaceExtendsNonExistentInterfaces()
      throws Exception {
    String js = "/** @interface \n" +
        " * @extends {nonExistent1} \n" +
        " * @extends {nonExistent2} \n" +
        " */function A() {}";
    String[] expectedWarnings = {
      "Bad type annotation. Unknown type nonExistent1",
      "Bad type annotation. Unknown type nonExistent2"
    };
    testTypes(js, expectedWarnings);
  }
}