package org.apache.commons.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import junit.framework.TestCase;

public class GeminiLocaleUtilsTest extends TestCase {

    public GeminiLocaleUtilsTest(String name) {
        super(name);
    }

    /**
     * Test that isAvailableLocale does not throw NullPointerException
     * when cAvailableLocaleSet has not been initialized.
     */
    public void testIsAvailableLocaleWithoutPriorSetInit() throws Exception {
        // Reset cAvailableLocaleSet to null via reflection to simulate
        // isAvailableLocale being called before availableLocaleSet() is called.
        Field setField = LocaleUtils.class.getDeclaredField("cAvailableLocaleSet");
        setField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(setField, setField.getModifiers() & ~Modifier.FINAL);
        setField.set(null, null);

        // In the buggy version, cAvailableLocaleSet is null, causing a NullPointerException.
        // In the fixed version, it calls availableLocaleList().contains(...) safely.
        assertTrue(LocaleUtils.isAvailableLocale(Locale.ENGLISH));
    }
}