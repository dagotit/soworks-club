package com.gmail.dlwk0807.dagotit.core.exception;

public class AuthenticationNotMatchException extends RuntimeException{
    public AuthenticationNotMatchException() {
        super();
    }

    public AuthenticationNotMatchException(String message) {
        super(message);
    }
}
