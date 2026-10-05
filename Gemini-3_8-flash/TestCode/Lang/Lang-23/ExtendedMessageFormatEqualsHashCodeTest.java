package org.apache.commons.lang3.text;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

/**
 * Regression test for LANG-636: ExtendedMessageFormat equals and hashCode.
 */
public class ExtendedMessageFormatEqualsHashCodeTest {

    private static class DummyFormat extends Format {
        private static final long serialVersionUID = 1L;

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo;
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return null;
        }
    }

    private static class DummyFormatFactory implements FormatFactory {
        private final String name;

        DummyFormatFactory(String name) {
            this.name = name;
        }

        @Override
        public Format getFormat(String name, String arguments, Locale locale) {
            return new DummyFormat();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return name.equals(((DummyFormatFactory) obj).name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentRegistries() {
        Map<String, FormatFactory> registry1 = Collections.singletonMap("fmt", new DummyFormatFactory("factory1"));
        Map<String, FormatFactory> registry2 = Collections.singletonMap("fmt", new DummyFormatFactory("factory2"));

        String pattern = "Message: {0,fmt}";
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat(pattern, Locale.US, registry1);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat(pattern, Locale.US, registry2);

        // In the buggy version, equals() and hashCode() are not overridden in ExtendedMessageFormat.
        // Therefore, MessageFormat.equals(Object) considers them equal because the underlying pattern and formats match,
        // ignoring the registry difference.
        assertFalse("ExtendedMessageFormat instances with different registries should not be equal", emf1.equals(emf2));
        assertFalse("ExtendedMessageFormat instances with different registries should have different hashCodes", emf1.hashCode() == emf2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithSameRegistry() {
        Map<String, FormatFactory> registry1 = new HashMap<String, FormatFactory>();
        registry1.put("fmt", new DummyFormatFactory("factory1"));

        Map<String, FormatFactory> registry2 = new HashMap<String, FormatFactory>();
        registry2.put("fmt", new DummyFormatFactory("factory1"));

        String pattern = "Message: {0,fmt}";
        ExtendedMessageFormat emf1 = new ExtendedMessageFormat(pattern, Locale.US, registry1);
        ExtendedMessageFormat emf2 = new ExtendedMessageFormat(pattern, Locale.US, registry2);

        assertTrue("ExtendedMessageFormat instances with equivalent fields should be equal", emf1.equals(emf2));
        assertTrue("ExtendedMessageFormat instances with equivalent fields should have the same hashCode", emf1.hashCode() == emf2.hashCode());
    }
}