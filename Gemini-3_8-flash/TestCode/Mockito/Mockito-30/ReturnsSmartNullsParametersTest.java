package org.mockito.internal.stubbing.defaultanswers;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.SmartNullPointerException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ReturnsSmartNullsParametersTest {

    interface Foo {
        Foo withArgs(String first, String second);
        int get();
    }

    @Test
    public void shouldPrintTheParametersOnSmartNullPointerExceptionMessage() {
        Foo mock = Mockito.mock(Foo.class, Mockito.RETURNS_SMART_NULLS);

        Foo smartNull = mock.withArgs("oompa", "lumpa");

        try {
            smartNull.get();
            fail("Expected SmartNullPointerException to be thrown");
        } catch (SmartNullPointerException ex) {
            String message = ex.getMessage();
            assertTrue(
                    "Exception message should include oompa and lumpa, but was: " + message,
                    message.contains("oompa, lumpa")
            );
        }
    }
}