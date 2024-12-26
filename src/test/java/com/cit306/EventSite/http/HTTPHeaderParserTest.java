package com.cit306.EventSite.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HTTPHeaderParserTest {

    private HttpParser httpParser;

    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException {
        httpParser = new HttpParser();
        Class<HttpParser> cls = HttpParser.class;
        parseHeadersMethod = cls.getDeclaredMethod("parseHeaders", InputStreamReader.class, HTTPRequest.class);
        parseHeadersMethod.setAccessible(true);
    }

    @Test
    public void testSimpleSingleHeader() throws InvocationTargetException, IllegalAccessException {
        HTTPRequest request = new HTTPRequest();
        parseHeadersMethod.invoke(
                httpParser,
                generateSimpleSingleHeaderMessage(),
                request);
        assertEquals(1, request.getHeaderNames().size());
        assertEquals("localhost:8080", request.getHeader("host"));
    }

    @Test
    public void testMultipleHeaders() throws InvocationTargetException, IllegalAccessException {
        HTTPRequest request = new HTTPRequest();
        parseHeadersMethod.invoke(
                httpParser,
                generateMultipleHeadersMessage(),
                request);
        assertEquals(10, request.getHeaderNames().size());
        assertEquals("localhost:8080", request.getHeader("host"));
    }



    private InputStreamReader generateSimpleSingleHeaderMessage(){
        String rawData =
                //"""
                //GET / HTTP/1.1\r
                "Host: localhost:8080\r\n";
                /*Connection: keep-alive\r
                sec-ch-ua: "Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"\r
                sec-ch-ua-mobile: ?0\r
                sec-ch-ua-platform: "Windows"\r
                DNT: 1\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,(*)/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Sec-Fetch-Dest: document\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                Cookie: Idea-d8159949=8efafafd-3594-4244-8cf7-4ca9a28c164c; Idea-d815994a=99e59e3d-3b20-4c6f-80e6-f7c5ccd8b2a3\
                \r
                """;
        */

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
    }

    private InputStreamReader generateMultipleHeadersMessage(){
        String rawData =
                """
                Host: localhost:8080\r
                Connection: keep-alive\r
                Upgrade-Insecure-Requests: 1\r
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36\r
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,(*)/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r
                Sec-Fetch-Site: none\r
                Sec-Fetch-Mode: navigate\r
                Sec-Fetch-User: ?1\r
                Accept-Encoding: gzip, deflate, br, zstd\r
                Accept-Language: en-NG,en-GB;q=0.9,en-US;q=0.8,en;q=0.7\r
                """;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
    }
}
