package org.mockitousage.annotation;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Issue205RegressionTest {

    static class CandidateType {
    }

    static class TargetObject {
        // candidate1 appears first in class fields declaration order
        CandidateType candidate1;
        CandidateType candidate2;
    }

    @InjectMocks
    private TargetObject target = new TargetObject();

    // Mock name matches candidate2, not candidate1
    @Mock
    private CandidateType candidate2;

    @Test
    public void shouldInjectMockIntoFieldWithMatchingNameWhenMultipleFieldsOfSameTypeExist() {
        MockitoAnnotations.initMocks(this);

        assertNull("candidate1 should not have been injected", target.candidate1);
        assertNotNull("candidate2 should have been injected", target.candidate2);
    }
}