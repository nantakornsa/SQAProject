package org.jsoup.nodes;

import org.junit.Test;

public class GeminiAttributeTest {

    @Test(expected = IllegalArgumentException.class)
    public void validatesKeysNotEmpty() {
        new Attribute(" ", "Check");
    }
}