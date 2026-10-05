package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StringUtilsLang703RegressionTest {

    @Test
    public void testJoin_ObjectArray_NullSeparator() {
        Object[] array = new Object[] {"foo", "bar"};
        assertEquals("foobar", StringUtils.join(array, (String) null));
        assertEquals("foobar", StringUtils.join(array, (String) null, 0, array.length));
    }
}