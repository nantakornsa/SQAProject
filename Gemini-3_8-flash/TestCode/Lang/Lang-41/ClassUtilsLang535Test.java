package org.apache.commons.lang;

import junit.framework.TestCase;

/**
 * Regression test for LANG-535.
 */
public class ClassUtilsLang535Test extends TestCase {

    public void testGetShortClassNameForArrays() {
        assertEquals("String[]", ClassUtils.getShortClassName(String[].class));
        assertEquals("int[]", ClassUtils.getShortClassName(int[].class));
        assertEquals("String[][]", ClassUtils.getShortClassName(String[][].class));
    }

    public void testGetPackageNameForArrays() {
        assertEquals("java.lang", ClassUtils.getPackageName(String[].class));
        assertEquals("java.lang", ClassUtils.getPackageName(String[][].class));
    }
}