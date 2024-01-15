package com.gmail.dlwk0807.dagotit.core.exception;

public class AccessDeniedCustomException extends RuntimeException {

    public AccessDeniedCustomException() {
        super();
    }

    public AccessDeniedCustomException(String message) {
        super(message);
    }
}
