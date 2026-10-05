package org.jsoup.helper;

import org.jsoup.Connection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpConnectionTest {

    @Test
    public void handlesHeaderEncodingOnRequest() {
        Connection.Request req = new HttpConnection.Request();
        req.addHeader("xxx", "é");
        assertEquals("é", req.header("xxx"));
    }
}