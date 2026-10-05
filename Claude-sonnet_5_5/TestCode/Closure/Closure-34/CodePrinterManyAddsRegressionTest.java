package com.google.javascript.jscomp;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;

import junit.framework.TestCase;

import java.util.List;

/**
 * Regression test for deeply nested left-associative ADD expressions
 * (Closure issue 691). The code generator used to recurse once per operator
 * and overflowed the stack on long chains of additions.
 */
public class CodePrinterManyAddsRegressionTest extends TestCase {

  private String printNode(Node n) {
    CompilerOptions options = new CompilerOptions();
    return new CodePrinter.Builder(n).setCompilerOptions(options).build();
  }

  public void testManyAdds() {
    int numAdds = 10000;
    List<String> numbers = Lists.newArrayList("0", "1");
    Node current = new Node(Token.ADD, Node.newNumber(0), Node.newNumber(1));
    for (int i = 2; i < numAdds; i++) {
      current = new Node(Token.ADD, current);

      // 1000 is printed as 1E3, and screws up our test.
      int num = i % 1000;
      numbers.add(String.valueOf(num));
      current.addChildToBack(Node.newNumber(num));
    }

    String expected = Joiner.on("+").join(numbers);
    String actual = printNode(current).replace("\n", "");
    assertEquals(expected, actual);
  }
}