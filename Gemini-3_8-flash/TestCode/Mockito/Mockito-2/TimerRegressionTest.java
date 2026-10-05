package org.mockito.internal.util;

import org.junit.Test;
import org.mockito.exceptions.misusing.FriendlyReminderException;

import static org.junit.Assert.fail;

public class TimerRegressionTest {

    @Test
    public void should_throw_friendly_reminder_exception_when_duration_is_negative() {
        try {
            new Timer(-1);
            fail("It is forbidden to create timer with negative value of timer's duration.");
        } catch (FriendlyReminderException ignored) {
            // Expected exception
        }
    }
}