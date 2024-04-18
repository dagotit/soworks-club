package com.gmail.dlwk0807.dagachi.core.exception;

public class CustomRespBodyException extends RuntimeException {
    public CustomRespBodyException() {
        super();
    }

    public CustomRespBodyException(String message) {
        super(message);
    }

    public CustomRespBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
