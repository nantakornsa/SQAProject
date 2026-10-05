package org.apache.commons.lang3.math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FractionLang22Test {

    @Test
    public void testReducedFractionWithMinIntegerNumerator() {
        Fraction f = Fraction.getReducedFraction(Integer.MIN_VALUE, 2);
        assertEquals(Integer.MIN_VALUE / 2, f.getNumerator());
        assertEquals(1, f.getDenominator());
    }

    @Test
    public void testReduceWithMinIntegerNumerator() {
        Fraction f = Fraction.getFraction(Integer.MIN_VALUE, 2).reduce();
        assertEquals(Integer.MIN_VALUE / 2, f.getNumerator());
        assertEquals(1, f.getDenominator());
    }
}