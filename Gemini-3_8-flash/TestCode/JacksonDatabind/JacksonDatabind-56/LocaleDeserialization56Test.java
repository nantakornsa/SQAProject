package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

public class LocaleDeserialization56Test extends BaseMapTest {

    public void testLocaleWithHyphen() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Testing BCP 47 style language tags with hyphen
        Locale loc = mapper.readValue(quote("en-US"), Locale.class);
        assertEquals(Locale.US, loc);
        assertEquals("en", loc.getLanguage());
        assertEquals("US", loc.getCountry());

        loc = mapper.readValue(quote("es-ES"), Locale.class);
        assertEquals(new Locale("es", "ES"), loc);
        assertEquals("es", loc.getLanguage());
        assertEquals("ES", loc.getCountry());

        // Also test with variant using hyphens
        loc = mapper.readValue(quote("en-US-variant"), Locale.class);
        assertEquals(new Locale("en", "US", "variant"), loc);
    }
}