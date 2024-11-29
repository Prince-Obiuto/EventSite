package com.cit306.EventSite.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HTTPVersion {
    HTTP_1_1("HTTP/1.1", 1, 1);

    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    HTTPVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    private static final Pattern httpversionRegexPattern = Pattern.compile("HTTP/(?<major>\\d+)\\.(?<minor>\\d+)");

    public static HTTPVersion getBestCompatibleVersion(String literalVersion) throws BadHTTPVersionException {
        Matcher matcher = httpversionRegexPattern.matcher(literalVersion);
        if (!matcher.find() || matcher.groupCount() != 2) {
            throw new BadHTTPVersionException();
        }
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        HTTPVersion tempBestCompatible = null;
        for (HTTPVersion version : HTTPVersion.values()) {
            if (version.LITERAL.equals(literalVersion)) {
                return version;
            } else {
                if (version.MAJOR == major) {
                    if (version.MINOR < minor) {
                        tempBestCompatible = version;
                    }
                }
            }
        }
        return tempBestCompatible;
    }
}
