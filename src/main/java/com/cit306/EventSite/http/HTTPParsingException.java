package com.cit306.EventSite.http;

public class HTTPParsingException extends Exception{

    private final HTTPStatusCodes errorCode;

    public HTTPParsingException(HTTPStatusCodes errorCode) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HTTPStatusCodes getErrorCode() {
        return errorCode;
    }
}
