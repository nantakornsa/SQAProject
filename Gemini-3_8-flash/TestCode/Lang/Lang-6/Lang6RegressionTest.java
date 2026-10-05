package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Lang6RegressionTest {

    @Test
    public void testEscapeSurrogatePairs() {
        assertEquals("\uD83D\uDE30", StringEscapeUtils.escapeCsv("\uD83D\uDE30"));
        assertEquals("\uD800\uDC00", StringEscapeUtils.escapeCsv("\uD800\uDC00"));
        assertEquals("\uD834\uDD1E", StringEscapeUtils.escapeCsv("\uD834\uDD1E"));
        assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeCsv("\uDBFF\uDFFD"));
    }
}