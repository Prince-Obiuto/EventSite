package com.cit306.EventSite.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPVersionTest {

    @Test
    void getBestCompatibleVersionExactMatch() {
        HTTPVersion version = null;
        try {
            version = HTTPVersion.getBestCompatibleVersion("HTTP/1.1");
        } catch (BadHTTPVersionException e) {
            fail();
        }

        assertNotNull(version);
        assertEquals(HTTPVersion.HTTP_1_1, version);
    }

    @Test
    void getBestCompatibleVersionBadFormat() {
        HTTPVersion version = null;
        try {
            version = HTTPVersion.getBestCompatibleVersion("http/1.1");
            fail();
        } catch (BadHTTPVersionException e) {
        }
    }

    @Test
    void getBestCompatibleVersionHigherVersion() {
        HTTPVersion version = null;
        try {
            version = HTTPVersion.getBestCompatibleVersion("HTTP/1.2");
            assertNotNull(version);
            assertEquals(HTTPVersion.HTTP_1_1, version);
        } catch (BadHTTPVersionException e) {
            fail();
        }
    }
}
