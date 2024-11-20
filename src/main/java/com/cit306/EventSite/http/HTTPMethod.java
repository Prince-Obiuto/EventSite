package com.cit306.EventSite.http;

public enum HTTPMethod {
    GET, HEAD;

    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for (HTTPMethod method : values()) {
            if (method.name().length() > tempMaxLength) {
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}
