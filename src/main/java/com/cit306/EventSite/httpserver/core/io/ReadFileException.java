package com.cit306.EventSite.httpserver.core.io;

import java.io.IOException;

public class ReadFileException extends Throwable {
    public ReadFileException() {
    }

    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadFileException(String message) {
        super(message);
    }

    public ReadFileException(Throwable cause) {
        super(cause);
    }

    public ReadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ReadFileException(IOException e) {

    }
}
