package com.fasterxml.jackson.databind.struct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnumFormatShape75Test extends BaseMapTest
{
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    enum NumberColor {
        RED,
        YELLOW,
        GREEN
    }

    static class ColorWrapper {
        public NumberColor color;

        public ColorWrapper(NumberColor color) {
            this.color = color;
        }
    }

    private final ObjectMapper MAPPER = new ObjectMapper();

    public void testEnumPropertyAsNumber() throws Exception {
        assertEquals(aposToQuotes("{'color':2}"),
                MAPPER.writeValueAsString(new ColorWrapper(NumberColor.GREEN)));
    }
}