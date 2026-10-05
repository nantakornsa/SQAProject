package org.jsoup.safety;

import org.jsoup.Jsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomProtocolsTest {

    @Test
    public void testCustomProtocolsAllowedByWhitelist() {
        String html = "<img src='cid:12345' /> <img src='data:gzzt' />";
        String dropped = Jsoup.clean(html, Whitelist.basicWithImages());
        assertEquals("<img /> \n<img />", dropped);

        String preserved = Jsoup.clean(html, Whitelist.basicWithImages().addProtocols("img", "src", "cid", "data"));
        assertEquals("<img src=\"cid:12345\" /> \n<img src=\"data:gzzt\" />", preserved);
    }
}