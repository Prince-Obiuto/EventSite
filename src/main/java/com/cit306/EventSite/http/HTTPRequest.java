package com.cit306.EventSite.http;

public class HTTPRequest extends HTTPMessage{

    private HTTPMethod method;
    private String requestTarget;
    private String httpVersion;

    HTTPRequest() {
    }

    public HTTPMethod getMethod() {
        return method;
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
}
