package com.cit306.EventSite.http;

public class HTTPRequest extends HTTPMessage{

    private HTTPMethod method;
    private String requestTarget;
    private String originalHttpVersion; //literal from the request
    private HTTPVersion bestCompatibleHttpVersion;

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
}
