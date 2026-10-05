package org.apache.commons.collections;

import junit.framework.TestCase;

/**
 * Regression test for COLLECTIONS-214: ExtendedProperties.setInclude()
 * modifying static/shared state instead of instance state.
 */
public class TestExtendedPropertiesInclude extends TestCase {

    public TestExtendedPropertiesInclude(String testName) {
        super(testName);
    }

    public void testInclude() {
        ExtendedProperties a = new ExtendedProperties();
        ExtendedProperties b = new ExtendedProperties();

        assertEquals("include", a.getInclude());
        assertEquals("include", b.getInclude());

        a.setInclude("import");
        assertEquals("import", a.getInclude());
        assertEquals("include", b.getInclude());

        a.setInclude("");
        assertNull(a.getInclude());
        assertEquals("include", b.getInclude());

        a.setInclude("hi");
        assertEquals("hi", a.getInclude());
        assertEquals("include", b.getInclude());

        a.setInclude(null);
        assertNull(a.getInclude());
        assertEquals("include", b.getInclude());
    }
}