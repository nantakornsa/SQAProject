package org.mockito.internal.verification.argumentmatching;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.internal.matchers.Equals;
import org.mockitoutil.TestBase;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArgumentMatchingToolTest extends TestBase {

    private ArgumentMatchingTool tool = new ArgumentMatchingTool();

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void shouldWorkFineWhenGivenArgIsNull() {
        // when
        Integer[] suspicious = tool.getSuspiciouslyNotMatchingArgsIndexes((List) Arrays.asList(new Equals(20)), new Object[] {null});

        // then
        assertEquals(0, suspicious.length);
    }
}