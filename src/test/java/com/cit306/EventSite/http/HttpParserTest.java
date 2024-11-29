package com.cit306.EventSite.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHTTPRequest() throws IOException {
        HTTPRequest request = null;
        try {
            request = httpParser.parseHTTPRequest(
                    generateValidGETTestCase()
            );
        } catch (HTTPParsingException e) {
            fail(e);
        }

        assertNotNull(request);
        assertEquals(HTTPMethod.GET, request.getMethod());
        assertEquals("/", request.getRequestTarget());
        assertEquals(HTTPVersion.HTTP_1_1, request.getBestCompatibleHttpVersion());
        assertEquals("HTTP/1.1", request.getOriginalHttpVersion());
    }

    @Test
    void parseHTTPRequestBadMethod1() throws IOException {
        try {
            HTTPRequest request = httpParser.parseHTTPRequest(
                    generateBadTestCaseMethodName1()
            );


            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestBadMethod2() throws IOException {
        try {
            HTTPRequest request = httpParser.parseHTTPRequest(
                    generateBadTestCaseMethodName2()
            );


            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestInvalidNumItems() throws IOException {
        try {
            HTTPRequest request = httpParser.parseHTTPRequest(
                    generateBadTestCaseRequestLineInvalidNumItems()
            );
            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPEmptyRequestLine() throws IOException {
        try {
            HTTPRequest request = httpParser.parseHTTPRequest(
                    generateBadTestCaseEmptyLine()
            );
            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestLineCRnoLF() throws IOException {
        try {
            HTTPRequest request = httpParser.parseHTTPRequest(
                    generateBadTestCaseRequestLineOnlyCRnoLF()
            );
            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestBadHTTPVersion() throws IOException {
        HTTPRequest request = null;
        try {
            request = httpParser.parseHTTPRequest(
                    generateBadHttpVersionTestCase()
            );
            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.CLIENT_ERROR_400_BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestUnsupportedHTTPVersion() throws IOException {
        HTTPRequest request = null;
        try {
            request = httpParser.parseHTTPRequest(
                    generateUnsupportedHttpVersionTestCase()
            );
            fail();
        } catch (HTTPParsingException e) {
            assertEquals(HTTPStatusCodes.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED, e.getErrorCode());
        }
    }

    @Test
    void parseHTTPRequestSupportedHTTPVersion1() throws IOException {
        HTTPRequest request = null;
        try {
            request = httpParser.parseHTTPRequest(
                    generateSupportedHttpVersion1()
            );
            assertNotNull(request);
            assertEquals(HTTPVersion.HTTP_1_1, request.getBestCompatibleHttpVersion());
            assertEquals("HTTP/1.2", request.getOriginalHttpVersion());
        } catch (HTTPParsingException e) {
            fail();
        }
    }

    private InputStream generateValidGETTestCase(){
        String rawData = """
                GET / HTTP/1.1\r
                Host: localhost:8080\r
                Connection: keep-alive\r
                sec-ch-ua: "Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"\r
                sec-ch-ua-mobile: ?0\r
                sec-ch-ua-platform: "Windows"\r
                DNT: 1\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Sec-Fetch-Dest: document\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                Cookie: Idea-d8159949=8efafafd-3594-4244-8cf7-4ca9a28c164c; Idea-d815994a=99e59e3d-3b20-4c6f-80e6-f7c5ccd8b2a3\
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName1(){
        String rawData = """
                GeT / HTTP/1.1\r
                Host: localhost:8080\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName2(){
        String rawData = """
                GETTTT / HTTP/1.1\r
                Host: localhost:8080\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCaseRequestLineInvalidNumItems(){
        String rawData = """
                GET / AAAAAA HTTP/1.1\r
                Host: localhost:8080\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCaseEmptyLine(){
        String rawData = """
                \r
                Host: localhost:8080\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadTestCaseRequestLineOnlyCRnoLF(){
        String rawData = "GET / HTTP/1.1\r" + // <----- no LF
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateBadHttpVersionTestCase(){
        String rawData = """
                GET / HTP/1.1\r
                Host: localhost:8080\r
                Connection: keep-alive\r
                sec-ch-ua: "Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"\r
                sec-ch-ua-mobile: ?0\r
                sec-ch-ua-platform: "Windows"\r
                DNT: 1\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Sec-Fetch-Dest: document\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                Cookie: Idea-d8159949=8efafafd-3594-4244-8cf7-4ca9a28c164c; Idea-d815994a=99e59e3d-3b20-4c6f-80e6-f7c5ccd8b2a3\
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateUnsupportedHttpVersionTestCase(){
        String rawData = """
                GET / HTTP/2.1\r
                Host: localhost:8080\r
                Connection: keep-alive\r
                sec-ch-ua: "Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"\r
                sec-ch-ua-mobile: ?0\r
                sec-ch-ua-platform: "Windows"\r
                DNT: 1\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Sec-Fetch-Dest: document\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                Cookie: Idea-d8159949=8efafafd-3594-4244-8cf7-4ca9a28c164c; Idea-d815994a=99e59e3d-3b20-4c6f-80e6-f7c5ccd8b2a3\
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    private InputStream generateSupportedHttpVersion1(){
        String rawData = """
                GET / HTTP/1.2\r
                Host: localhost:8080\r
                Connection: keep-alive\r
                sec-ch-ua: "Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"\r
                sec-ch-ua-mobile: ?0\r
                sec-ch-ua-platform: "Windows"\r
                DNT: 1\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Sec-Fetch-Dest: document\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                Cookie: Idea-d8159949=8efafafd-3594-4244-8cf7-4ca9a28c164c; Idea-d815994a=99e59e3d-3b20-4c6f-80e6-f7c5ccd8b2a3\
                \r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}

