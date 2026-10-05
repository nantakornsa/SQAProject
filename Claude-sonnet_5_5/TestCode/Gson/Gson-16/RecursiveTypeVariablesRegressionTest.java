package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import junit.framework.TestCase;

/**
 * Regression test for infinite recursion when resolving recursive type variables
 * (Gson pull request 1128).
 */
public class RecursiveTypeVariablesRegressionTest extends TestCase {

  public void testRecursiveTypeVariablesResolveSingleVariable() throws Exception {
    TypeAdapter<TestType> adapter = new Gson().getAdapter(TestType.class);
    assertNotNull(adapter);
  }

  public void testRecursiveTypeVariablesResolveTwoVariables() throws Exception {
    TypeAdapter<TestType2> adapter = new Gson().getAdapter(TestType2.class);
    assertNotNull(adapter);
  }

  private static class TestType<X> {
    TestType<? super X> superType;
  }

  private static class TestType2<X, Y> {
    TestType2<? super Y, ? super X> superReversedType;
  }
}