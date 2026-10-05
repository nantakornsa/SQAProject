package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DataUtilTest {

    @Test
    public void supportsBOMinInputStream() throws IOException {
        // UTF-16BE with BOM
        byte[] utf16be = new byte[]{
            (byte) 0xFE, (byte) 0xFF,
            0x00, '<', 0x00, 't', 0x00, 'i', 0x00, 't', 0x00, 'l', 0x00, 'e', 0x00, '>',
            0x00, 'B', 0x00, 'O', 0x00, 'M', 0x00, ' ', 0x00, 'T', 0x00, 'e', 0x00, 's', 0x00, 't',
            0x00, '<', 0x00, '/', 0x00, 't', 0x00, 'i', 0x00, 't', 0x00, 'l', 0x00, '>',
            0x00, '<', 0x00, 'p', 0x00, '>',
            0x00, 'H', 0x00, 'e', 0x00, 'l', 0x00, 'l', 0x00, 'o',
            0x00, '<', 0x00, '/', 0x00, 'p', 0x00, '>'
        };

        Document doc = Jsoup.parse(new ByteArrayInputStream(utf16be), null, "http://example.com");
        assertEquals("BOM Test", doc.title());
        assertEquals("Hello", doc.text());

        // UTF-16LE with BOM
        byte[] utf16le = new byte[]{
            (byte) 0xFF, (byte) 0xFE,
            '<', 0x00, 't', 0x00, 'i', 0x00, 't', 0x00, 'l', 0x00, 'e', 0x00, '>', 0x00,
            'B', 0x00, 'O', 0x00, 'M', 0x00, ' ', 0x00, 'T', 0x00, 'e', 0x00, 's', 0x00, 't', 0x00,
            '<', 0x00, '/', 0x00, 't', 0x00, 'i', 0x00, 't', 0x00, 'l', 0x00, '>', 0x00,
            '<', 0x00, 'p', 0x00, '>', 0x00,
            'H', 0x00, 'e', 0x00, 'l', 0x00, 'l', 0x00, 'o', 0x00,
            '<', 0x00, '/', 0x00, 'p', 0x00, '>', 0x00
        };

        doc = Jsoup.parse(new ByteArrayInputStream(utf16le), null, "http://example.com");
        assertEquals("BOM Test", doc.title());
        assertEquals("Hello", doc.text());
    }
}