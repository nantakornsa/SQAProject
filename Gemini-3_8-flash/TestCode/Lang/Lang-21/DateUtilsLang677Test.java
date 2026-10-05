package org.apache.commons.lang3.time;

import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import org.junit.Test;

public class DateUtilsLang677Test {

    @Test
    public void testIsSameLocalTimeWithDifferentAmPm() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.set(2004, Calendar.JULY, 9, 4, 0, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.set(2004, Calendar.JULY, 9, 16, 0, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        // Calendar.HOUR is identical (4) for both 4 AM and 4 PM (16:00).
        // DateUtils.isSameLocalTime must distinguish between them using HOUR_OF_DAY.
        assertFalse("DateUtils.isSameLocalTime should return false for 4:00 and 16:00",
                DateUtils.isSameLocalTime(cal1, cal2));
    }
}