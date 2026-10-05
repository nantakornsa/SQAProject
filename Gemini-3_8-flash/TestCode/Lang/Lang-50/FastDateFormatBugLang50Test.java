package org.apache.commons.lang.time;

import java.util.Locale;
import junit.framework.TestCase;

public class FastDateFormatBugLang50Test extends TestCase {

    public void testChangeDefaultLocaleDateInstance() {
        Locale realDefaultLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            FastDateFormat format1 = FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.GERMANY);
            FastDateFormat format2 = FastDateFormat.getDateInstance(FastDateFormat.FULL);
            Locale.setDefault(Locale.GERMANY);
            FastDateFormat format3 = FastDateFormat.getDateInstance(FastDateFormat.FULL);

            assertSame(Locale.GERMANY, format1.getLocale());
            assertSame(Locale.US, format2.getLocale());
            assertSame(Locale.GERMANY, format3.getLocale());
            assertTrue(format1 != format2);
            assertTrue(format2 != format3);
        } finally {
            Locale.setDefault(realDefaultLocale);
        }
    }

    public void testChangeDefaultLocaleDateTimeInstance() {
        Locale realDefaultLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            FastDateFormat format1 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.GERMANY);
            FastDateFormat format2 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
            Locale.setDefault(Locale.GERMANY);
            FastDateFormat format3 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);

            assertSame(Locale.GERMANY, format1.getLocale());
            assertSame(Locale.US, format2.getLocale());
            assertSame(Locale.GERMANY, format3.getLocale());
            assertTrue(format1 != format2);
            assertTrue(format2 != format3);
        } finally {
            Locale.setDefault(realDefaultLocale);
        }
    }
}