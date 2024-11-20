package com.cit306.EventSite.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; //32
    private static final int CR = 0x0D; //13
    private static final int LF = 0x0A; //10

    public HTTPRequest parseHTTPRequest(InputStream inputStream) throws HTTPParsingException, IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HTTPRequest request = new HTTPRequest();

        try {
            parseRequestLine(reader, request);
        } catch (HTTPParsingException e) {
            throw new HTTPParsingException(e.getErrorCode());
        }
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HTTPRequest request) throws IOException, HTTPParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    LOGGER.debug("Request Line VERSION to Process:{}", processingDataBuffer);
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HTTPParsingException(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }

            if (_byte == SP) {
                //TODO Process previous data
                if (!methodParsed) {
                    LOGGER.debug("Request Line METHOD to Process:{}", processingDataBuffer);
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request Line REQ TARGET to Process:{}", processingDataBuffer);
                    requestTargetParsed = true;
                } else {
                    throw new HTTPParsingException(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char)_byte);
                if (!methodParsed) {
                    if (processingDataBuffer.length() > HTTPMethod.MAX_LENGTH) {
                        throw new HTTPParsingException(HTTPStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader reader, HTTPRequest request) {
    }

    private void parseBody(InputStreamReader reader, HTTPRequest request) {
    }
}
