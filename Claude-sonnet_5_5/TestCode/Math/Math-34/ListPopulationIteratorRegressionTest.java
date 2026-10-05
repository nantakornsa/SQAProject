package org.apache.commons.math3.genetics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ListPopulationIteratorRegressionTest {

    private static class SimpleChromosome extends Chromosome {
        private final double value;

        SimpleChromosome(double value) {
            this.value = value;
        }

        public double fitness() {
            return value;
        }
    }

    private ListPopulation createPopulation() {
        final List<Chromosome> chromosomes = new ArrayList<Chromosome>();
        chromosomes.add(new SimpleChromosome(1.0));
        chromosomes.add(new SimpleChromosome(2.0));
        chromosomes.add(new SimpleChromosome(3.0));

        final ListPopulation population = new ListPopulation(10) {
            public Population nextGeneration() {
                // not important
                return null;
            }
        };
        population.addChromosomes(chromosomes);
        return population;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveUnsupported() {
        final ListPopulation population = createPopulation();

        final Iterator<Chromosome> iter = population.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
    }

    @Test
    public void testIteratorRemoveDoesNotModifyPopulation() {
        final ListPopulation population = createPopulation();

        final Iterator<Chromosome> iter = population.iterator();
        Assert.assertTrue(iter.hasNext());
        iter.next();
        try {
            iter.remove();
            Assert.fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        Assert.assertEquals(3, population.getPopulationSize());
    }
}