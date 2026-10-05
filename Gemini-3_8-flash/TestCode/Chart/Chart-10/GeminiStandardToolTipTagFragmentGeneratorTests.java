package org.jfree.chart.imagemap.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;

/**
 * Tests for the {@link StandardToolTipTagFragmentGenerator} class.
 */
public class GeminiStandardToolTipTagFragmentGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GeminiStandardToolTipTagFragmentGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GeminiStandardToolTipTagFragmentGeneratorTests(String name) {
        super(name);
    }

    /**
     * Tests that tool tip text containing double quotes is properly HTML-escaped.
     */
    public void testGenerateToolTipFragmentEscaping() {
        StandardToolTipTagFragmentGenerator generator = new StandardToolTipTagFragmentGenerator();
        assertEquals(
            " title=\"Series &quot;A&quot;, 100.0\" alt=\"\"",
            generator.generateToolTipFragment("Series \"A\", 100.0")
        );
    }
}