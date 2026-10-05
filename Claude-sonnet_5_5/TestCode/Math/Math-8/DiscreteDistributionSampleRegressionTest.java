package org.apache.commons.math3.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression test for MATH-942: DiscreteDistribution.sample(int) must not
 * fail with ArrayStoreException when the singletons are instances of
 * different (e.g. anonymous) subclasses.
 */
public class DiscreteDistributionSampleRegressionTest {

    @Test
    public void testIssue942() {
        List<Pair<Object, Double>> list = new ArrayList<Pair<Object, Double>>();
        list.add(new Pair<Object, Double>(new Object() {}, new Double(0)));
        list.add(new Pair<Object, Double>(new Object() {}, new Double(1)));
        Assert.assertEquals(1, new DiscreteDistribution<Object>(list).sample(1).length);
    }

    @Test
    public void testSampleMultipleWithDifferentRuntimeClasses() {
        List<Pair<Object, Double>> list = new ArrayList<Pair<Object, Double>>();
        list.add(new Pair<Object, Double>(new Object() {}, new Double(0.5)));
        list.add(new Pair<Object, Double>(new Object() {}, new Double(0.5)));
        Object[] sample = new DiscreteDistribution<Object>(list).sample(50);
        Assert.assertEquals(50, sample.length);
    }
}