package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.test.BaseTest;

public class FilteringParserMatchCountTest extends BaseTest {
    private final JsonFactory JSON_F = new JsonFactory();

    public void testSingleMatchFilteringWithPath() throws Exception {
        String json = "{'a': 123, 'b': [1, 2], 'c': {'d': 321}}".replace("'", "\"");
        JsonParser p0 = JSON_F.createParser(json);
        FilteringParserDelegate p = new FilteringParserDelegate(
                p0,
                new JsonPointerBasedFilter("/b/1"),
                true, // includePath
                false // allowMultipleMatches
        );

        while (p.nextToken() != null) {
            // consume tokens
        }
        assertEquals(1, p.getMatchCount());
    }

    public void testNotAllowMultipleMatchesWithoutPath() throws Exception {
        String json = "{'a': 2, 'b': 3, 'c': 4}".replace("'", "\"");
        JsonParser p0 = JSON_F.createParser(json);
        FilteringParserDelegate p = new FilteringParserDelegate(
                p0,
                new TokenFilter() {
                    @Override
                    public TokenFilter includeProperty(String name) {
                        return ("a".equals(name) || "c".equals(name)) ? TokenFilter.INCLUDE_ALL : null;
                    }
                },
                false, // includePath
                false // allowMultipleMatches
        );

        StringBuilder sb = new StringBuilder();
        while (p.nextToken() != null) {
            sb.append(p.getText()).append(" ");
        }
        assertEquals("2 ", sb.toString());
        assertEquals(1, p.getMatchCount());
    }
}