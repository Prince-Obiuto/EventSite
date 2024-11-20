package com.cit306.EventSite.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    public HTTPRequest parseHTTPRequest(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HTTPRequest request = new HTTPRequest();

        parseRequestLine(reader, request);
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HTTPRequest request) {
    }

    private void parseHeaders(InputStreamReader reader, HTTPRequest request) {
    }

    private void parseBody(InputStreamReader reader, HTTPRequest request) {
    }
}
