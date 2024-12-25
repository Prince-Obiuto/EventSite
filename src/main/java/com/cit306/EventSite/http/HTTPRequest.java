package com.cit306.EventSite.http;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class HTTPRequest extends HTTPMessage{

    private HTTPMethod method;
    private String requestTarget;
    private String originalHttpVersion; //literal from the request
    private HTTPVersion bestCompatibleHttpVersion;
    private final HashMap<String, String> headers = new HashMap<>();

    HTTPRequest() {
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public HTTPVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }


    void setMethod(String methodName) throws HTTPParsingException {
        for (HTTPMethod method: HTTPMethod.values()) {
            if (methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HTTPParsingException(
                HTTPStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    void setRequestTarget(String requestTarget) throws HTTPParsingException {
        if (requestTarget == null || requestTarget.isEmpty()) {
            throw new HTTPParsingException(HTTPStatusCodes.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

    void setHttpVersion(String originalHttpVersion) throws BadHTTPVersionException, HTTPParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HTTPVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            throw new HTTPParsingException(HTTPStatusCodes.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    void addHeader(String headerName, String headerField) {
        headers.put(headerName.toLowerCase(), headerField);
    }
}
