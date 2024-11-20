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

    void setMethod(HTTPMethod method) {
        this.method = method;
    }
}
